package com.agomez.backendapp.employemanagementback.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(min = 8, max = 16)
    private String password;
//
//    @Column(name = "is_enabled")
//    private boolean isEnabled;
//
//    @Column(name = "account_no_expired")
//    private boolean accountNoExpired;
//
//    @Column(name = "account_no_locked")
//    private boolean accountNoLocked;
//
//    @Column(name = "credential_no_expired")
//    private boolean credentialNoExpired;


    @OneToOne(mappedBy = "user")
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    private Employee employee;

    @ManyToMany()
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonManagedReference
    private List<Role> roles;

    public User(String username, String password, Employee employee, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.employee = employee;
        this.roles = roles;
    }

    public User() {

    }
}
