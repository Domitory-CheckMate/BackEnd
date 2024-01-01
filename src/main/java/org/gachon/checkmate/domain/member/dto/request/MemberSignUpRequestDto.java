package org.gachon.checkmate.domain.member.dto.request;

import org.gachon.checkmate.domain.member.entity.GenderType;
import org.gachon.checkmate.domain.member.entity.MbtiType;

public record MemberSignUpRequestDto(
        String email,
        String password,
        String name,
        String school,
        String major,
        MbtiType mbtiType,
        GenderType genderType
) {
}
