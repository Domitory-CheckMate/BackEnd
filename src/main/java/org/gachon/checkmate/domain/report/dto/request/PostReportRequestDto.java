package org.gachon.checkmate.domain.report.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PostReportRequestDto (
        @NotNull(message = "신고할 게시물의 ID를 입력해주세요.") Long postId,
        @NotEmpty(message = "신고할 이유를 입력해주세요") String reason
) {
}
