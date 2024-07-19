package com.agomez.backendapp.employemanagementback.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotNull
    @Column(name = "phone_number")
    private int phoneNumber;

    @NotNull
    @Column(name = "hire_date")
    private Date hireDate;

    @NotNull
    private BigDecimal salary;

    @ManyToOne( optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", unique = true, nullable = false)
    private Department departmentId;

    @ManyToOne()
    private Role role;

}
