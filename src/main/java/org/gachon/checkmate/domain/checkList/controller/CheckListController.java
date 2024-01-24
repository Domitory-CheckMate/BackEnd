package org.gachon.checkmate.domain.checkList.controller;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.checkList.dto.request.CheckListRequestDto;
import org.gachon.checkmate.domain.checkList.dto.response.CheckListResponseDto;
import org.gachon.checkmate.domain.checkList.service.CheckListService;
import org.gachon.checkmate.global.common.SuccessResponse;
import org.gachon.checkmate.global.config.auth.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/checkList")
@RestController
public class CheckListController {
    private final CheckListService checkListService;

    @PostMapping("/new")
    public ResponseEntity<SuccessResponse<?>> createCheckList(@UserId final Long userId,
                                                              @RequestBody final CheckListRequestDto checkListRequestDto) {
        checkListService.createCheckList(userId, checkListRequestDto);
        return SuccessResponse.created(null);
    }

    @PatchMapping("/my")
    public ResponseEntity<SuccessResponse<?>> updateCheckList(@UserId final Long userId,
                                                              @RequestBody final CheckListRequestDto checkListRequestDto) {
        System.out.println("my logs " + checkListRequestDto.cleanType());
        checkListService.updateCheckList(userId, checkListRequestDto);
        return SuccessResponse.ok(null);
    }

    @GetMapping("/my")
    public ResponseEntity<SuccessResponse<?>> getCheckList(@UserId final Long userId) {
        final CheckListResponseDto checkListResponseDto = checkListService.getCheckList(userId);
        return SuccessResponse.ok(checkListResponseDto);
    }

}
