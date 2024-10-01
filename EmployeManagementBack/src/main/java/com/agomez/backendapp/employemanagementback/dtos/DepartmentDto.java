package com.agomez.backendapp.employemanagementback.dtos;

import com.agomez.backendapp.employemanagementback.entities.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.agomez.backendapp.employemanagementback.entities.Department}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DepartmentDto(@NotNull String name, Set<EmployeeDepartmentDto> employees) implements Serializable {
  }