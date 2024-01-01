package org.gachon.checkmate.domain.member.dto.response;

import lombok.Builder;

@Builder
public record MemberSignInResponseDto(
        Long memberId,
        String accessToken,
        String refreshToken
) {
    public static MemberSignInResponseDto of(Long memberId,
                                             String accessToken,
                                             String refreshToken) {
        return MemberSignInResponseDto.builder()
                .memberId(memberId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
