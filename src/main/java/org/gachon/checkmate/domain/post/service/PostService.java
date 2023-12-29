package org.gachon.checkmate.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.checkList.entity.CheckList;
import org.gachon.checkmate.domain.checkList.entity.PostCheckList;
import org.gachon.checkmate.domain.checkList.repository.CheckListRepository;
import org.gachon.checkmate.domain.post.dto.response.PostSearchResponseDto;
import org.gachon.checkmate.domain.post.dto.support.PostSearchDto;
import org.gachon.checkmate.domain.post.repository.PostQuerydslRepository;
import org.gachon.checkmate.global.error.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static org.gachon.checkmate.global.error.ErrorCode.CHECK_LIST_NOT_FOUND;


@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final CheckListRepository checkListRepository;
    private final PostQuerydslRepository postQuerydslRepository;

    public List<PostSearchResponseDto> searchTextPost(Long userId, String text, Pageable pageable) {
        CheckList checkList = getCheckList(userId);
        Page<PostSearchDto> postSearchDtoList = getPostSearchDto(text, pageable);
        return createPostSearchResponseDto(postSearchDtoList, checkList);
    }

    private List<PostSearchResponseDto> createPostSearchResponseDto(Page<PostSearchDto> postSearchDtoList, CheckList checkList) {
        return postSearchDtoList.stream()
                .map(postSearchDto ->
                        PostSearchResponseDto.of(
                                postSearchDto,
                                getRemainDate(postSearchDto.endDate()),
                                getAccuracy(postSearchDto.postCheckList(), checkList)
                        ))
                .collect(Collectors.toList());
    }

    private int getAccuracy(PostCheckList postCheckList, CheckList checkList) {
        int count = 0;
        count += getRateForFrequencyElement(postCheckList.getCleanType().getCode(), checkList.getCleanType().getCode());
        count += getRateForFrequencyElement(postCheckList.getDrinkType().getCode(), checkList.getDrinkType().getCode());
        count += getRateForFrequencyElement(postCheckList.getHomeType().getCode(), checkList.getHomeType().getCode());
        count = postCheckList.getLifePatterType().equals(checkList.getLifePatterType()) ? count + 1 : count;
        count = postCheckList.getNoiseType().equals(checkList.getNoiseType()) ? count + 1 : count;
        count = postCheckList.getSleepType().equals(checkList.getSleepType()) ? count + 1 : count;
        return (int) (count / 6) * 100;
    }

    private int getRateForFrequencyElement(String firstEnumCode, String secondEnumCode) {
        return 1 - Math.abs(Integer.parseInt(firstEnumCode) - Integer.parseInt(secondEnumCode));
    }

    private int getRemainDate(LocalDate endDate) {
        return (int) endDate.until(LocalDate.now(), ChronoUnit.DAYS);
    }

    private Page<PostSearchDto> getPostSearchDto(String text, Pageable pageable) {
        return postQuerydslRepository.searchTextPost(text, pageable);
    }

    private CheckList getCheckList(Long userId) {
        return checkListRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(CHECK_LIST_NOT_FOUND));
    }
}
