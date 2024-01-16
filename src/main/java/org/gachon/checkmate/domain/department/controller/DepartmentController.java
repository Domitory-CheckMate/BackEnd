package org.gachon.checkmate.domain.department.controller;


import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.department.dto.response.DepartmentNameResponseDto;
import org.gachon.checkmate.domain.department.service.DepartmentService;
import org.gachon.checkmate.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("")
    public ResponseEntity<SuccessResponse<?>> getDepartments(@RequestParam(value = "univ") final String univ,
                                                             @RequestParam(value = "searchText") final String searchText) {
        List<DepartmentNameResponseDto> responseDtos = departmentService.getDepartments(univ, searchText);
        return SuccessResponse.ok(responseDtos);
    }
}
