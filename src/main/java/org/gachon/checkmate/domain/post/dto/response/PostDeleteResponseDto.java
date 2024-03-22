package org.gachon.checkmate.domain.post.dto.response;

import lombok.Builder;

@Builder
public record PostDeleteResponseDto(
        Boolean isSuccess
) {
    public static PostDeleteResponseDto of(Boolean isSuccess) {
        return PostDeleteResponseDto.builder()
                .isSuccess(isSuccess)
                .build();
    }
}
