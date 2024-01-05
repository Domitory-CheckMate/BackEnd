package org.gachon.checkmate.domain.member.dto.response;

import lombok.Builder;

@Builder
public record MemberReissueTokenResponseDto(
        String accessToken,
        String refreshToken
) {
    public static MemberReissueTokenResponseDto of(String accessToken, String refreshToken){
        return MemberReissueTokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
