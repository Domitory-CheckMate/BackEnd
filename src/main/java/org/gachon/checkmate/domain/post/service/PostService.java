package org.gachon.checkmate.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.checkList.dto.response.CheckListResponseDto;
import org.gachon.checkmate.domain.checkList.entity.CheckList;
import org.gachon.checkmate.domain.checkList.entity.PostCheckList;
import org.gachon.checkmate.domain.checkList.repository.CheckListRepository;
import org.gachon.checkmate.domain.post.dto.response.PostDetailResponseDto;
import org.gachon.checkmate.domain.post.dto.response.PostSearchElementResponseDto;
import org.gachon.checkmate.domain.post.dto.response.PostSearchResponseDto;
import org.gachon.checkmate.domain.post.dto.support.PostDetailDto;
import org.gachon.checkmate.domain.post.dto.support.PostSearchDto;
import org.gachon.checkmate.domain.post.entity.ImportantKeyType;
import org.gachon.checkmate.domain.post.entity.SortType;
import org.gachon.checkmate.domain.post.repository.PostQuerydslRepository;
import org.gachon.checkmate.global.error.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.gachon.checkmate.global.error.ErrorCode.CHECK_LIST_NOT_FOUND;
import static org.gachon.checkmate.global.error.ErrorCode.POST_NOT_FOUND;
import static org.gachon.checkmate.global.utils.EnumValueUtils.toEntityCode;
import static org.gachon.checkmate.global.utils.PagingUtils.convertPaging;


@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final CheckListRepository checkListRepository;
    private final PostQuerydslRepository postQuerydslRepository;

    public PostSearchResponseDto getAllPosts(Long userId, String type, Pageable pageable) {
        CheckList checkList = getCheckList(userId);
        SortType sortType = toEntityCode(SortType.class, type);
        Page<PostSearchDto> postSearchList = getAllPostsResults(pageable);
        List<PostSearchElementResponseDto> searchResults = createPostSearchResponseDto(postSearchList, checkList);
        sortByTypeForSearchResults(searchResults, Objects.requireNonNull(sortType));
        List<PostSearchElementResponseDto> pagingSearchResults
                = convertPaging(searchResults, pageable.getOffset(), pageable.getPageSize());
        return PostSearchResponseDto.of(pagingSearchResults, postSearchList.getTotalPages(), postSearchList.getTotalElements());
    }

    public PostDetailResponseDto getPostDetails(Long postId) {
        PostDetailDto postDetailDto = getPostDetailDto(postId);
        CheckListResponseDto checkListResponseDto = createCheckListResponseDto(postDetailDto.postCheckList());
        return PostDetailResponseDto.of(postDetailDto, checkListResponseDto);
    }

    public PostSearchResponseDto searchKeyWordPost(Long userId, String key, String type, Pageable pageable) {
        CheckList checkList = getCheckList(userId);
        SortType sortType = toEntityCode(SortType.class, type);
        ImportantKeyType importantKeyType = toEntityCode(ImportantKeyType.class, key);
        Page<PostSearchDto> postSearchList = getKeySearchResults(importantKeyType, pageable);
        List<PostSearchElementResponseDto> searchResults = createPostSearchResponseDto(postSearchList, checkList);
        sortByTypeForSearchResults(searchResults, Objects.requireNonNull(sortType));
        List<PostSearchElementResponseDto> pagingSearchResults
                = convertPaging(searchResults, pageable.getOffset(), pageable.getPageSize());
        return PostSearchResponseDto.of(pagingSearchResults, postSearchList.getTotalPages(), postSearchList.getTotalElements());
    }

    public PostSearchResponseDto searchTextPost(Long userId, String text, Pageable pageable) {
        CheckList checkList = getCheckList(userId);
        Page<PostSearchDto> postSearchList = getTextSearchResults(text, pageable);
        List<PostSearchElementResponseDto> searchResults = createPostSearchResponseDto(postSearchList, checkList);
        return PostSearchResponseDto.of(searchResults, postSearchList.getTotalPages(), postSearchList.getTotalElements());
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

    private CheckListResponseDto createCheckListResponseDto(PostCheckList postCheckList) {
        return CheckListResponseDto.of(
                postCheckList.getCleanType().getDesc(),
                postCheckList.getDrinkType().getDesc(),
                postCheckList.getHomeType().getDesc(),
                postCheckList.getLifePatterType().getDesc(),
                postCheckList.getNoiseType().getDesc(),
                postCheckList.getSleepType().getDesc()
        );
    }

    private void sortByTypeForSearchResults(List<PostSearchElementResponseDto> posts, SortType sortType) {
        if (sortType.equals(SortType.ACCURACY))
            sortByAccuracyType(posts);
        else if (sortType.equals(SortType.REMAIN_DATE))
            sortByRemainDate(posts);
    }

    private void sortByAccuracyType(List<PostSearchElementResponseDto> posts) {
        posts.sort(Comparator.comparingInt(PostSearchElementResponseDto::accuracy));
    }

    private void sortByRemainDate(List<PostSearchElementResponseDto> posts) {
        posts.sort(Comparator.comparingInt(PostSearchElementResponseDto::remainDate));
    }

    private int getAccuracy(PostCheckList postCheckList, CheckList checkList) {
        int count = 0;
        count += getRateForFrequencyElement(postCheckList.getCleanType().getCode(), checkList.getCleanType().getCode(), 4);
        count += getRateForFrequencyElement(postCheckList.getDrinkType().getCode(), checkList.getDrinkType().getCode(), 3);
        count += getRateForFrequencyElement(postCheckList.getHomeType().getCode(), checkList.getHomeType().getCode(), 3);
        count = postCheckList.getLifePatterType().equals(checkList.getLifePatterType()) ? count + 1 : count;
        count = postCheckList.getNoiseType().equals(checkList.getNoiseType()) ? count + 1 : count;
        count = postCheckList.getSleepType().equals(checkList.getSleepType()) ? count + 1 : count;
        return (int) (count / 6) * 100;
    }

    private int getRateForFrequencyElement(String firstEnumCode, String secondEnumCode, int size) {
        return 1 - Math.abs(Integer.parseInt(firstEnumCode) - Integer.parseInt(secondEnumCode)) / size;
    }

    private int getRemainDate(LocalDate endDate) {
        return (int) endDate.until(LocalDate.now(), ChronoUnit.DAYS);
    }

    private Page<PostSearchDto> getAllPostsResults(Pageable pageable) {
        return postQuerydslRepository.findAllPosts(pageable);
    }

    private Page<PostSearchDto> getKeySearchResults(ImportantKeyType importantKeyType, Pageable pageable) {
        return postQuerydslRepository.searchKeyPost(importantKeyType, pageable);
    }

    private Page<PostSearchDto> getTextSearchResults(String text, Pageable pageable) {
        return postQuerydslRepository.searchTextPost(text, pageable);
    }

    private CheckList getCheckList(Long userId) {
        return checkListRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(CHECK_LIST_NOT_FOUND));
    }

    private PostDetailDto getPostDetailDto(Long postId) {
        return postQuerydslRepository.findPostDetail(postId)
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));
    }
}
