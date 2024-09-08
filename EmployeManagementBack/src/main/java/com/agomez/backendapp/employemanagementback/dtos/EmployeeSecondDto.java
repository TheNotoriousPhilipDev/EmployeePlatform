package com.agomez.backendapp.employemanagementback.dtos;

import com.agomez.backendapp.employemanagementback.entities.Department;
import com.agomez.backendapp.employemanagementback.entities.EmployeeImage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.agomez.backendapp.employemanagementback.entities.Employee}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EmployeeSecondDto(@NotBlank String firstName, String lastName,
                                @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}", flags = Pattern.Flag.CASE_INSENSITIVE) @NotBlank String email,
                                long phoneNumber, @NotNull Date hireDate,
                                Department department, EmployeeImage employeeImage) implements Serializable {
}//revisar si debo retornar la imagen también