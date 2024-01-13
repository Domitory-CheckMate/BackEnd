package org.gachon.checkmate.domain.report.dto.request;

public record ChatRoomReportRequestDto(
        String chatRoomId,
        String reason
) {
}
