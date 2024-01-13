package org.gachon.checkmate.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.checkList.dto.request.CheckListRequestDto;
import org.gachon.checkmate.domain.checkList.dto.response.CheckListResponseDto;
import org.gachon.checkmate.domain.checkList.entity.CheckList;
import org.gachon.checkmate.domain.checkList.entity.PostCheckList;
import org.gachon.checkmate.domain.checkList.repository.CheckListRepository;
import org.gachon.checkmate.domain.checkList.repository.PostCheckListRepository;
import org.gachon.checkmate.domain.member.entity.User;
import org.gachon.checkmate.domain.member.repository.UserRepository;
import org.gachon.checkmate.domain.post.dto.request.PostCreateRequestDto;
import org.gachon.checkmate.domain.post.dto.request.PostUpdateRequestDto;
import org.gachon.checkmate.domain.post.dto.request.PostStateUpdateRequestDto;
import org.gachon.checkmate.domain.post.dto.response.*;
import org.gachon.checkmate.domain.post.dto.support.PostDetailDto;
import org.gachon.checkmate.domain.post.dto.support.PostPagingSearchCondition;
import org.gachon.checkmate.domain.post.dto.support.PostSearchCondition;
import org.gachon.checkmate.domain.post.dto.support.PostSearchDto;
import org.gachon.checkmate.domain.post.entity.Post;
import org.gachon.checkmate.domain.post.entity.SortType;
import org.gachon.checkmate.domain.post.repository.PostRepository;
import org.gachon.checkmate.domain.scrap.repository.ScrapRepository;
import org.gachon.checkmate.global.error.exception.EntityNotFoundException;
import org.gachon.checkmate.global.error.exception.ForbiddenException;
import org.gachon.checkmate.global.error.exception.InvalidValueException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.gachon.checkmate.global.error.ErrorCode.*;
import static org.gachon.checkmate.global.utils.PagingUtils.convertPaging;


@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final UserRepository userRepository;
    private final CheckListRepository checkListRepository;
    private final PostRepository postRepository;
    private final PostCheckListRepository postCheckListRepository;
    private final ScrapRepository scrapRepository;

    public void createPost(Long userId, PostCreateRequestDto requestDto) {
        validateDuplicateTitle(requestDto.title());
        validateAvailableEndDate(requestDto.endDate());
        User user = getUserOrThrow(userId);
        Post post = createPostAndSave(requestDto, user);
        createPostCheckListAndSave(requestDto.checkList(), post);
    }

    public PostSearchResponseDto getMyPosts(Long userId, Pageable pageable) {
        CheckList checkList = getCheckList(userId);
        PostPagingSearchCondition condition = PostPagingSearchCondition.searchSelectedUser(userId, pageable);
        Page<PostSearchDto> postSearchList = getTextSearchResults(condition);
        List<PostSearchElementResponseDto> searchResults = createPostSearchResponseDto(postSearchList, checkList);
        return PostSearchResponseDto.of(searchResults, postSearchList.getTotalPages(), postSearchList.getTotalElements());
    }

    public PostSearchResponseDto getAllPosts(Long userId, String key, String type, String gender, Pageable pageable) {
        CheckList checkList = getCheckList(userId);
        PostSearchCondition condition = PostSearchCondition.of(type, key, gender, pageable);
        Page<PostSearchDto> postSearchList = getSearchResults(condition);
        List<PostSearchElementResponseDto> searchResults = createPostSearchResponseDto(postSearchList, checkList);
        sortByTypeForSearchResults(searchResults, condition.sortType());
        List<PostSearchElementResponseDto> pagingSearchResults
                = convertPaging(searchResults, pageable.getOffset(), pageable.getPageSize());
        return PostSearchResponseDto.of(pagingSearchResults, postSearchList.getTotalPages(), postSearchList.getTotalElements());
    }

    public PostDetailResponseDto getPostDetails(Long userId, Long postId) {
        PostDetailDto postDetailDto = getPostDetailDto(postId);
        CheckListResponseDto checkListResponseDto = createCheckListResponseDto(postDetailDto.postCheckList());
        boolean isScrapPost = existPostInScrap(postId, userId);
        return PostDetailResponseDto.of(postDetailDto, checkListResponseDto, isScrapPost);
    }

    public PostSearchResponseDto searchTextPost(Long userId, String text, Pageable pageable) {
        CheckList checkList = getCheckList(userId);
        PostPagingSearchCondition condition = PostPagingSearchCondition.searchText(text, pageable);
        Page<PostSearchDto> postSearchList = getTextSearchResults(condition);
        List<PostSearchElementResponseDto> searchResults = createPostSearchResponseDto(postSearchList, checkList);
        return PostSearchResponseDto.of(searchResults, postSearchList.getTotalPages(), postSearchList.getTotalElements());
    }

    public PostUpdateResponseDto updateMyPost(Long userId, Long postId, PostUpdateRequestDto requestDto) {
        User user = getUserOrThrow(userId);
        Post post = getPostOrThrow(postId);
        validatePostWriter(user, post);
        validateAvailableEndDate(requestDto.endDate());
        post.updatePost(requestDto);
        CheckListResponseDto checkListResponseDto = createCheckListResponseDto(post.getPostCheckList());
        return PostUpdateResponseDto.of(post, checkListResponseDto);
    }

    public PostStateUpdateResponseDto updateMyPostState(Long userId, Long postId, PostStateUpdateRequestDto requestDto) {
        User user = getUserOrThrow(userId);
        Post post = getPostOrThrow(postId);
        validatePostWriter(user, post);
        post.updatePostState(requestDto);
        return PostStateUpdateResponseDto.of(post);
    }

    private void validatePostWriter(User user, Post post) {
        if(!post.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException(NOT_POST_WRITER);
        }
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
                postCheckList.getLifePatternType().getDesc(),
                postCheckList.getNoiseType().getDesc(),
                postCheckList.getSleepType().getDesc(),
                postCheckList.getSmokeType().getDesc()
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

    private void validateDuplicateTitle(String title) {
        if (postRepository.existsByTitle(title))
            throw new InvalidValueException(INVALID_POST_TITLE);
    }

    private void validateAvailableEndDate(LocalDate endDate) {
        LocalDate now = LocalDate.now();
        if (endDate.isBefore(now))
            throw new InvalidValueException(INVALID_POST_DATE);
    }

    private Post createPostAndSave(PostCreateRequestDto postCreateRequestDto, User user) {
        Post post = Post.createPost(postCreateRequestDto, user);
        postRepository.save(post);
        return post;
    }

    private void createPostCheckListAndSave(CheckListRequestDto checkListRequestDto, Post post) {
        PostCheckList postCheckList = PostCheckList.createPostCheckList(checkListRequestDto, post);
        postCheckListRepository.save(postCheckList);
    }

    private Page<PostSearchDto> getSearchResults(PostSearchCondition condition) {
        return postRepository.searchPosts(condition);
    }

    private Page<PostSearchDto> getTextSearchResults(PostPagingSearchCondition condition) {
        return postRepository.searchPostsWithPaging(condition);
    }

    private CheckList getCheckList(Long userId) {
        return checkListRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(CHECK_LIST_NOT_FOUND));
    }

    private PostDetailDto getPostDetailDto(Long postId) {
        return postRepository.findPostDetail(postId)
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    private boolean existPostInScrap(Long postId, Long userId) {
        return scrapRepository.existsByPostIdAndUserId(postId, userId);
    }

    private Post getPostOrThrow(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));
    }
}
