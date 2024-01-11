package org.gachon.checkmate.domain.chat.dto.response;

import lombok.Builder;

@Builder
public record ChatTotalNotReadResponseDto(
        Long notReadCount

) {
    public static ChatTotalNotReadResponseDto of(Long notReadCount) {
        return ChatTotalNotReadResponseDto.builder()
                .notReadCount(notReadCount)
                .build();
    }
}
