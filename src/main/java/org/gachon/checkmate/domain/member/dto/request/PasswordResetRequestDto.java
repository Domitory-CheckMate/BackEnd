package org.gachon.checkmate.domain.member.dto.request;

public record PasswordResetRequestDto(
        String email,
        String newPassword
) {
}
