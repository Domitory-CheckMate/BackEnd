package org.gachon.checkmate.domain.department.service;

import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.department.dto.response.DepartmentNameResponseDto;
import org.gachon.checkmate.domain.department.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<DepartmentNameResponseDto> getDepartments(String univ, String department) {
        return getDepartmentsName(univ, department);
    }

    private List<DepartmentNameResponseDto> getDepartmentsName(String univ, String department) {
        return departmentRepository.findDepartmentName(univ, department);
    }
}
