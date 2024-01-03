package org.gachon.checkmate.domain.checkList.controller;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.checkList.dto.request.CreateCheckListRequestDto;
import org.gachon.checkmate.domain.checkList.service.CheckListService;
import org.gachon.checkmate.global.common.SuccessResponse;
import org.gachon.checkmate.global.config.auth.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/checkList")
@RestController
public class CheckListController {
    private final CheckListService checkListService;

    @PostMapping("/new")
    public ResponseEntity<SuccessResponse<?>> createCheckList(@UserId final Long userId,
                                                              @RequestBody final CreateCheckListRequestDto createCheckListRequestDto) {
        checkListService.createCheckList(userId, createCheckListRequestDto);
        return SuccessResponse.ok(null);
    }

}
