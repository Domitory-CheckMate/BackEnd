package org.gachon.checkmate.domain.report.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.report.dto.request.ChatRoomReportRequestDto;
import org.gachon.checkmate.domain.report.dto.request.PostReportRequestDto;
import org.gachon.checkmate.domain.report.service.ReportService;
import org.gachon.checkmate.global.common.SuccessResponse;
import org.gachon.checkmate.global.config.auth.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/report")
@RestController
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/post")
    public ResponseEntity<SuccessResponse<?>> reportPost(@UserId Long userId,
                                                         @RequestBody @Valid PostReportRequestDto requestDto) {
        reportService.reportPost(userId, requestDto);
        return SuccessResponse.created(null);
    }

    @PostMapping("/chat-room")
    public ResponseEntity<SuccessResponse<?>> reportChatRoom(@UserId Long userId,
                                                         @RequestBody @Valid ChatRoomReportRequestDto requestDto) {
        reportService.reportChatRoom(userId, requestDto);
        return SuccessResponse.created(null);
    }
}
