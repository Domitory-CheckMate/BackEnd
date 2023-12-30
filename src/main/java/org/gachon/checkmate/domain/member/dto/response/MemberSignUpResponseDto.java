package org.gachon.checkmate.domain.member.dto.response;

import lombok.Builder;

@Builder
public record MemberSignUpResponseDto(
        Long memberId,
        String name,
        String accessToken,
        String refreshToken
) {
    public static MemberSignUpResponseDto of(Long memberId,
                                             String name,
                                             String accessToken,
                                             String refreshToken) {
        return MemberSignUpResponseDto.builder()
                .memberId(memberId)
                .name(name)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
