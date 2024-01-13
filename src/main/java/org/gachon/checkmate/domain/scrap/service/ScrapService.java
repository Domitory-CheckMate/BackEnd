package org.gachon.checkmate.domain.scrap.service;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.checkList.entity.CheckList;
import org.gachon.checkmate.domain.checkList.entity.PostCheckList;
import org.gachon.checkmate.domain.checkList.repository.CheckListRepository;
import org.gachon.checkmate.domain.member.entity.User;
import org.gachon.checkmate.domain.member.repository.UserRepository;
import org.gachon.checkmate.domain.post.dto.response.PostSearchElementResponseDto;
import org.gachon.checkmate.domain.post.dto.response.PostSearchResponseDto;
import org.gachon.checkmate.domain.post.dto.support.PostSearchDto;
import org.gachon.checkmate.domain.post.entity.Post;
import org.gachon.checkmate.domain.post.repository.PostRepository;
import org.gachon.checkmate.domain.scrap.dto.request.ScrapRequestDto;
import org.gachon.checkmate.domain.scrap.dto.support.ScrapSearchCondition;
import org.gachon.checkmate.domain.scrap.entity.Scrap;
import org.gachon.checkmate.domain.scrap.repository.ScrapRepository;
import org.gachon.checkmate.global.error.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static org.gachon.checkmate.global.error.ErrorCode.*;

@RequiredArgsConstructor
@Transactional
@Service
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CheckListRepository checkListRepository;

    public PostSearchResponseDto getScrapPosts(Long userId, Pageable pageable) {
        CheckList checkList = getCheckList(userId);
        ScrapSearchCondition condition = ScrapSearchCondition.of(userId, pageable);
        Page<PostSearchDto> myScrapPosts = findMyScrapPosts(condition);
        List<PostSearchElementResponseDto> searchResults = createPostSearchResponseDto(myScrapPosts, checkList);
        return PostSearchResponseDto.of(searchResults, myScrapPosts.getTotalPages(), myScrapPosts.getTotalElements());
    }

    public void creatScrapPost(Long userId, ScrapRequestDto scrapRequestDto) {
        User user = findUserOrThrow(userId);
        Post post = findPostOrThrow(scrapRequestDto.postId());
        createScrapAndSave(user, post);
    }

    public void deleteScrapPost(Long userId, Long scrapId) {
        scrapRepository.deleteByIdAndUserId(scrapId, userId);
    }

    private List<PostSearchElementResponseDto> createPostSearchResponseDto(Page<PostSearchDto> postSearchDtoList, CheckList checkList) {
        return postSearchDtoList.stream()
                .map(postSearchDto ->
                        PostSearchElementResponseDto.of(
                                postSearchDto,
                                getRemainDate(postSearchDto.endDate()),
                                getAccuracy(postSearchDto.postCheckList(), checkList)))
                .collect(Collectors.toList());
    }

    private int getAccuracy(PostCheckList postCheckList, CheckList checkList) {
        int count = 0;
        count += getRateForFrequencyElement(postCheckList.getCleanType().getCode(), checkList.getCleanType().getCode(), 4);
        count += getRateForFrequencyElement(postCheckList.getDrinkType().getCode(), checkList.getDrinkType().getCode(), 3);
        count += getRateForFrequencyElement(postCheckList.getHomeType().getCode(), checkList.getHomeType().getCode(), 3);
        count = postCheckList.getLifePatternType().equals(checkList.getLifePatternType()) ? count + 1 : count;
        count = postCheckList.getNoiseType().equals(checkList.getNoiseType()) ? count + 1 : count;
        count = postCheckList.getSleepType().equals(checkList.getSleepType()) ? count + 1 : count;
        return (int) (count / 6) * 100;
    }

    private int getRateForFrequencyElement(String firstEnumCode, String secondEnumCode, int size) {
        return 1 - Math.abs(Integer.parseInt(firstEnumCode) - Integer.parseInt(secondEnumCode)) / size;
    }

    private int getRemainDate(LocalDate endDate) {
        return (int) LocalDate.now().until(endDate, ChronoUnit.DAYS);
    }

    private void createScrapAndSave(User user, Post post) {
        Scrap scrap = Scrap.createScrap(user, post);
        scrapRepository.save(scrap);
    }

    private CheckList getCheckList(Long userId) {
        return checkListRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(CHECK_LIST_NOT_FOUND));
    }

    private Page<PostSearchDto> findMyScrapPosts(ScrapSearchCondition condition) {
        return scrapRepository.searchMyScrapPosts(condition);
    }

    private User findUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    private Post findPostOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));
    }
}
