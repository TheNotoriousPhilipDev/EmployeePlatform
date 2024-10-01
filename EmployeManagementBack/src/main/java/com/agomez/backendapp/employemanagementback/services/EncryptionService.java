package com.agomez.backendapp.employemanagementback.services;

import com.agomez.backendapp.employemanagementback.dtos.EmployeeDto;

public interface EncryptionService {

    String encryptPassword(EmployeeDto employeeDto);
}
