package org.gachon.checkmate.domain.member.dto.response;

import lombok.Builder;

@Builder
public record MemberSignUpResponseDto(
        String name,
        String accessToken,
        String refreshToken
) {
    public static MemberSignUpResponseDto of(String name,
                                             String accessToken,
                                             String refreshToken) {
        return MemberSignUpResponseDto.builder()
                .name(name)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
