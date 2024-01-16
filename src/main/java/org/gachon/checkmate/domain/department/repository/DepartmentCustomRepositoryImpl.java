package org.gachon.checkmate.domain.department.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.gachon.checkmate.domain.department.dto.response.DepartmentNameResponseDto;
import org.gachon.checkmate.domain.department.dto.response.QDepartmentNameResponseDto;

import java.util.List;

import static org.gachon.checkmate.domain.department.entity.QDepartment.department;


@RequiredArgsConstructor
public class DepartmentCustomRepositoryImpl implements DepartmentCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<DepartmentNameResponseDto> findDepartmentName(String univ, String searchDepartText) {
        return jpaQueryFactory
                .select(new QDepartmentNameResponseDto(
                        department.departmentName,
                        department.college
                ))
                .from(department)
                .where(
                        eqUniversity(univ),
                        containsDepartmentName(searchDepartText)
                )
                .fetch();
    }

    private BooleanExpression eqUniversity(String univ) {
        return department.university.eq(univ);
    }

    private BooleanExpression containsDepartmentName(String searchDepartText) {
        return department.departmentName.containsIgnoreCase(searchDepartText);
    }
}
