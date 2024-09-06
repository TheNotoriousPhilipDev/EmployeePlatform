package com.agomez.backendapp.employemanagementback.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigInteger;
import java.util.Date;


@Entity
@Table(name = "employees")
@Getter
@Setter
@ToString
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
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotNull
    @Column(name = "phone_number")
    private long phoneNumber;

    @NotNull
    @Column(name = "hire_date")
    private Date hireDate;

    @NotNull
    private BigInteger salary;

    @ManyToOne( optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)//checked
    @JsonManagedReference
    private Department department;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)//checked
    private Role role;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_image_id", unique = true)
    private EmployeeImage employeeImage;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", unique = true)
    @JsonManagedReference
    private User user;

    public Employee(String firstName, String lastName, String email, long phoneNumber, Date hireDate, BigInteger salary, Department department, Role role, EmployeeImage employeeImage, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.salary = salary;
        this.department = department;
        this.role = role;
        this.employeeImage = employeeImage;
        this.user = user;
    }

    public Employee() {
    }
}
