package com.agomez.backendapp.employemanagementback.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.agomez.backendapp.employemanagementback.entities.EmployeeImage}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EmployeeImageSecondDto(String s3ObjectUrl, String name, Date creationDate) implements Serializable {
}