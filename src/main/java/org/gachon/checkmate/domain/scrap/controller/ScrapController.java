package org.gachon.checkmate.domain.scrap.controller;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.post.dto.response.PostSearchResponseDto;
import org.gachon.checkmate.domain.scrap.dto.request.ScrapRequestDto;
import org.gachon.checkmate.domain.scrap.service.ScrapService;
import org.gachon.checkmate.global.common.SuccessResponse;
import org.gachon.checkmate.global.config.auth.UserId;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/scrap")
@RestController
public class ScrapController {
    private final ScrapService scrapService;

    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getScrapPosts(@UserId final Long userId,
                                                            final Pageable pageable) {
        final PostSearchResponseDto responseDto = scrapService.getScrapPosts(userId, pageable);
        return SuccessResponse.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createScrapPost(@UserId final Long userId,
                                                              @RequestBody final ScrapRequestDto requestDto) {
        scrapService.creatScrapPost(userId, requestDto);
        return SuccessResponse.created(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> deleteScrapPost(@PathVariable("id") final Long scrapId) {
        scrapService.deleteScrapPost(scrapId);
        return SuccessResponse.ok(null);
    }
}
