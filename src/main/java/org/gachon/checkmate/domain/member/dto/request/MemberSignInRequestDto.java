package org.gachon.checkmate.domain.member.dto.request;

public record MemberSignInRequestDto(
        String email,
        String password
) {
}
