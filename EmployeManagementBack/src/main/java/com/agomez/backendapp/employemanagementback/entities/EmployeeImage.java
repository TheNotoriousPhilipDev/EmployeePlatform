package com.agomez.backendapp.employemanagementback.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "employees_images")
@Getter
@Setter
@ToString
public class EmployeeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String s3ObjectUrl;

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public EmployeeImage(String s3ObjectUrl, String name, Date creationDate) {
        this.s3ObjectUrl = s3ObjectUrl;
        this.name = name;
        this.creationDate = creationDate;
    }

    public EmployeeImage() {

    }
}
