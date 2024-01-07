package org.gachon.checkmate.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.post.dto.request.PostCreateRequestDto;
import org.gachon.checkmate.domain.post.dto.response.PostDetailResponseDto;
import org.gachon.checkmate.domain.post.dto.response.PostSearchResponseDto;
import org.gachon.checkmate.domain.post.service.PostService;
import org.gachon.checkmate.global.common.SuccessResponse;
import org.gachon.checkmate.global.config.auth.UserId;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getAllPosts(@UserId final Long userId,
                                                          @RequestParam(required = false) final String key,
                                                          @RequestParam final String type,
                                                          @RequestParam(required = false) final String gender,
                                                          final Pageable pageable) {
        final PostSearchResponseDto responseDto = postService.getAllPosts(userId, key, type, gender, pageable);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> getPostDetails(@PathVariable("id") final Long postId) {
        final PostDetailResponseDto responseDto = postService.getPostDetails(postId);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/search")
    public ResponseEntity<SuccessResponse<?>> searchTextPost(@UserId final Long userId,
                                                             @RequestParam final String text,
                                                             final Pageable pageable) {
        final PostSearchResponseDto responseDto = postService.searchTextPost(userId, text, pageable);
        return SuccessResponse.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createPost(@UserId final Long userId,
                                                         @RequestBody @Valid final PostCreateRequestDto requestDto) {
        postService.createPost(userId, requestDto);
        return SuccessResponse.ok(null);
    }
}
