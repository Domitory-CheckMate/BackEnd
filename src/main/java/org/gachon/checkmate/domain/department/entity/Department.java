package org.gachon.checkmate.domain.department.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long id;
    @Column(name = "university")
    private String university;
    @Column(name = "department_name")
    private String departmentName;
    @Column(name = "college")
    private String college;
    @Column(name = "university_series")
    private String universitySeries;
}
