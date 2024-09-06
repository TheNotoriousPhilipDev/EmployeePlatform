package com.agomez.backendapp.employemanagementback.dtos;

import com.agomez.backendapp.employemanagementback.entities.Department;
import com.agomez.backendapp.employemanagementback.entities.EmployeeImage;
import com.agomez.backendapp.employemanagementback.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * DTO for {@link com.agomez.backendapp.employemanagementback.entities.Employee}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EmployeeDto(@NotBlank String firstName, @NotBlank String lastName, String username, String password,
                          @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}", flags = Pattern.Flag.CASE_INSENSITIVE) @NotBlank String email,
                          String phoneNumber, @NotNull String hireDate, @NotNull String salary, @NotNull String departmentName,
                          @NotNull String role, MultipartFile multipartFile) implements Serializable {
}