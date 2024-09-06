package com.agomez.backendapp.employemanagementback.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.agomez.backendapp.employemanagementback.entities.EmployeeImage}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EmployeeImageDto(@NotNull String s3ObjectUrl, @NotNull Date creationDate) implements Serializable {
}