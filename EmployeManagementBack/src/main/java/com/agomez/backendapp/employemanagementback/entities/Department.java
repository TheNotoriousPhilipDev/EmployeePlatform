package com.agomez.backendapp.employemanagementback.entities;

import com.agomez.backendapp.employemanagementback.enums.KindOfDepartment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "department")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind_of_department", nullable = false)
    @NotNull
    private KindOfDepartment name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Employee> employees = new HashSet<>();

    public Department(KindOfDepartment name, Set<Employee> employees) {
        this.name = name;
        this.employees = employees;
    }

    public Department() {
    }
}
