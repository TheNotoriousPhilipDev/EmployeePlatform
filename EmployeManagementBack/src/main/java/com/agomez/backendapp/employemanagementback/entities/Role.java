package com.agomez.backendapp.employemanagementback.entities;

import com.agomez.backendapp.employemanagementback.enums.KindOfRole;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind_of_role", nullable = false)
    private KindOfRole kindOfRole;  


    public Role(KindOfRole kindOfRole) {
        this.kindOfRole = kindOfRole;
    }

    public Role() {

    }
}
