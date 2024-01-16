package org.gachon.checkmate.domain.department.repository;

import org.gachon.checkmate.domain.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long>, DepartmentCustomRepository {
    List<Department> findByCollege(String college);
}
