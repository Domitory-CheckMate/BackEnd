package org.gachon.checkmate.domain.department.repository;

import org.gachon.checkmate.domain.department.dto.response.DepartmentNameResponseDto;

import java.util.List;

public interface DepartmentCustomRepository {

    List<DepartmentNameResponseDto> findDepartmentName(String univ, String searchDepartText);
}
