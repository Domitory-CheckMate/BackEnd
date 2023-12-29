package org.gachon.checkmate.domain.post.controller;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.post.dto.response.PostSearchResponseDto;
import org.gachon.checkmate.domain.post.service.PostService;
import org.gachon.checkmate.global.common.SuccessResponse;
import org.gachon.checkmate.global.config.auth.UserId;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class PostController {
    private final PostService postService;

    @GetMapping("/search")
    public ResponseEntity<SuccessResponse<?>> searchTextPost(@UserId final Long userId,
                                                             @RequestParam final String text,
                                                             final Pageable pageable) {
        final List<PostSearchResponseDto> responseDto = postService.searchTextPost(userId, text, pageable);
        return SuccessResponse.ok(responseDto);
    }
}
