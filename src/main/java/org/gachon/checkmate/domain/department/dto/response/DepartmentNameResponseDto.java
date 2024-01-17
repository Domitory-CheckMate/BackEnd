package org.gachon.checkmate.domain.department.dto.response;

import com.querydsl.core.annotations.QueryProjection;

public record DepartmentNameResponseDto(
        String department,
        String college
) {
    @QueryProjection
    public DepartmentNameResponseDto(String department, String college) {
        this.department = department;
        this.college = college;
    }
}
