package org.gachon.checkmate.domain.post.dto.response;

import lombok.Builder;
import org.gachon.checkmate.domain.post.dto.support.PostSearchDto;

@Builder
public record PostSearchElementResponseDto(
        String title,
        String content,
        String importantKey,
        String similarityKey,
        int scrapCount,
        int remainDate,
        int accuracy
) {
    public static PostSearchElementResponseDto of(PostSearchDto postSearchDto,
                                                  int remainDate,
                                                  int accuracy) {
        return PostSearchElementResponseDto.builder()
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
