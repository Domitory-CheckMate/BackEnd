package org.gachon.checkmate.domain.report.dto.request;

public record PostReportRequestDto (
        Long postId,
        String reason
) {
}
