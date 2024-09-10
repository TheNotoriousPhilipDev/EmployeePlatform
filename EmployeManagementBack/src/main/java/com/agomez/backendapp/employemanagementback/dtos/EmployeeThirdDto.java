package com.agomez.backendapp.employemanagementback.dtos;

import com.agomez.backendapp.employemanagementback.entities.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

/**
 * DTO for {@link Employee}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record EmployeeThirdDto(@NotBlank String firstName, String lastName, @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}", flags = Pattern.Flag.CASE_INSENSITIVE)
@NotBlank String email, @Digits(message = "Phone number must have exactly 10 digits", integer = 10, fraction = 0) Long phoneNumber) implements Serializable {
  }