package org.gachon.checkmate.domain.post.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PostSearchResponseDto(
        List<PostSearchElementResponseDto> results,
        int totalPages,
        long totalElements) {
    public static PostSearchResponseDto of(List<PostSearchElementResponseDto> results, int totalPages, long totalElements) {
        return PostSearchResponseDto.builder()
                .results(results)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }
}
