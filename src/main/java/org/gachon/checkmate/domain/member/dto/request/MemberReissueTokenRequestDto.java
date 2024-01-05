package org.gachon.checkmate.domain.member.dto.request;

public record MemberReissueTokenRequestDto(
        String accessToken,
        String refreshToken
) {
}
