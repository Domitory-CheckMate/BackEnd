package org.gachon.checkmate.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.checkList.dto.request.CheckListRequestDto;
import org.gachon.checkmate.domain.checkList.dto.response.CheckListResponseDto;
import org.gachon.checkmate.domain.checkList.dto.support.CheckListEnumDto;
import org.gachon.checkmate.domain.checkList.entity.CheckList;
import org.gachon.checkmate.domain.checkList.entity.PostCheckList;
import org.gachon.checkmate.domain.checkList.repository.CheckListRepository;
import org.gachon.checkmate.domain.checkList.repository.PostCheckListRepository;
import org.gachon.checkmate.domain.member.entity.User;
import org.gachon.checkmate.domain.member.repository.UserRepository;
import org.gachon.checkmate.domain.post.dto.request.PostRequestDto;
import org.gachon.checkmate.domain.post.dto.request.PostStateUpdateRequestDto;
import org.gachon.checkmate.domain.post.dto.response.*;
import org.gachon.checkmate.domain.post.dto.support.*;
import org.gachon.checkmate.domain.post.entity.Post;
import org.gachon.checkmate.domain.post.repository.PostRepository;
import org.gachon.checkmate.domain.post.utils.PostSortingUtils;
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
import java.util.List;
import java.util.stream.Collectors;

import static org.gachon.checkmate.domain.checkList.utils.MatchRateCalculator.getAccuracy;
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

    public void createPost(Long userId, PostRequestDto requestDto) {
        PostEnumDto postEnumDto = PostRequestDto.toEnumDto(requestDto);
        validateDuplicateTitle(postEnumDto.title());
        validateAvailableEndDate(postEnumDto.endDate());
        User user = getUserOrThrow(userId);
        Post post = createPostAndSave(postEnumDto, user);
        createPostCheckListAndSave(postEnumDto.checkList(), post);
    }

    public PostSearchResponseDto getMyPosts(Long userId, Pageable pageable) {
        CheckList checkList = getCheckList(userId);
        PostPagingSearchCondition condition = PostPagingSearchCondition.searchSelectedUser(userId, pageable);
        Page<PostSearchDto> postSearchList = getTextSearchResults(condition);
        List<PostSearchElementResponseDto> searchResults = createPostSearchResponseDto(postSearchList, checkList);
        return PostSearchResponseDto.of(searchResults, postSearchList.getTotalPages(), postSearchList.getTotalElements());
    }

    public PostSearchResponseDto getAllPosts(Long userId, String key, String type, String gender, String dormitory, Pageable pageable) {
        CheckList checkList = getCheckList(userId);
        PostSearchCondition condition = PostSearchCondition.of(type, key, gender, dormitory, pageable);
        Page<PostSearchDto> postSearchList = getSearchResults(condition);
        List<PostSearchElementResponseDto> searchResults = createPostSearchResponseDto(postSearchList, checkList);
        PostSortingUtils.sortByTypeForSearchResults(searchResults, condition.postSortType());
        List<PostSearchElementResponseDto> pagingSearchResults
                = convertPaging(searchResults, pageable.getOffset(), pageable.getPageSize());
        return PostSearchResponseDto.of(pagingSearchResults, postSearchList.getTotalPages(), postSearchList.getTotalElements());
    }

    public PostDetailResponseDto getPostDetails(Long userId, Long postId) {
        PostDetailDto postDetailDto = getPostDetailDto(postId);
        CheckListResponseDto checkListResponseDto = CheckListResponseDto.ofPostCheckList(postDetailDto.postCheckList());
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

    public PostUpdateResponseDto updateMyPost(Long userId, Long postId, PostRequestDto requestDto) {
        User user = getUserOrThrow(userId);
        Post post = getPostOrThrow(postId);
        PostEnumDto postEnumDto = PostRequestDto.toEnumDto(requestDto);
        validatePostWriter(user, post);
        validateAvailableEndDate(postEnumDto.endDate());
        post.updatePost(postEnumDto);
        CheckListResponseDto checkListResponseDto = CheckListResponseDto.ofPostCheckList(post.getPostCheckList());
        return PostUpdateResponseDto.of(post, checkListResponseDto);
    }

    public PostStateUpdateResponseDto updateMyPostState(Long userId, Long postId, PostStateUpdateRequestDto requestDto) {
        User user = getUserOrThrow(userId);
        Post post = getPostOrThrow(postId);
        validatePostWriter(user, post);
        post.updatePostState(requestDto);
        return PostStateUpdateResponseDto.of(post);
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

    private int getRemainDate(LocalDate endDate) {
        return (int) LocalDate.now().until(endDate, ChronoUnit.DAYS);
    }

    private void validatePostWriter(User user, Post post) {
        if (!post.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException(NOT_POST_WRITER);
        }
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

    private Post createPostAndSave(PostEnumDto postRequestDto, User user) {
        Post post = Post.createPost(postRequestDto, user);
        postRepository.save(post);
        return post;
    }

    private void createPostCheckListAndSave(CheckListEnumDto checkListRequestDto, Post post) {
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
