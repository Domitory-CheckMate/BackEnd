package org.gachon.checkmate.domain.scrap.dto.support;

import lombok.Builder;
import org.springframework.data.domain.Pageable;

@Builder
public record ScrapSearchCondition(
        Long userId,
        Pageable pageable
) {
    public static ScrapSearchCondition of(Long userId, Pageable pageable) {
        return ScrapSearchCondition.builder()
                .userId(userId)
                .pageable(pageable)
                .build();
    }
}
