package org.gachon.checkmate.domain.post.dto.response;

import lombok.Builder;
import org.gachon.checkmate.domain.post.dto.support.PostSearchDto;

@Builder
public record PostSearchResponseDto(
        String title,
        String content,
        String importantKey,
        String similarityKey,
        int scrapCount,
        int remainDate,
        int accuracy
) {
    public static PostSearchResponseDto of(PostSearchDto postSearchDto,
                                           int remainDate,
                                           int accuracy) {
        return PostSearchResponseDto.builder()
                .title(postSearchDto.title())
                .content(postSearchDto.content())
                .importantKey(postSearchDto.importantKey().getDesc())
                .similarityKey(postSearchDto.similarityKey().getDesc())
                .scrapCount(postSearchDto.scrapCount())
                .remainDate(remainDate)
                .accuracy(accuracy)
                .build();
    }
}
