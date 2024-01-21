package org.gachon.checkmate.domain.member.dto.response;

import lombok.Builder;
import org.gachon.checkmate.domain.member.entity.GenderType;

@Builder
public record MemberSignInResponseDto(
        Long memberId,
        String gender,
        String accessToken,
        String refreshToken
) {
    public static MemberSignInResponseDto of(Long memberId,
                                             GenderType genderType,
                                             String accessToken,
                                             String refreshToken) {
        return MemberSignInResponseDto.builder()
                .memberId(memberId)
                .gender(genderType.getDesc())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
