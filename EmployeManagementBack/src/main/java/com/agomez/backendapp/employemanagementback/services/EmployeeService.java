package com.agomez.backendapp.employemanagementback.services;

import com.agomez.backendapp.employemanagementback.dtos.EmployeeDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeSecondDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeThirdDto;
import com.agomez.backendapp.employemanagementback.entities.Employee;
import com.agomez.backendapp.employemanagementback.exceptions.FileUploadException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    //big fat interface
    Employee saveEmployee(EmployeeDto employeeDto) throws FileUploadException, ParseException, IOException;

    Optional<EmployeeSecondDto> findById(Long id);

    List<EmployeeSecondDto> findAll();

    void deleteEmployeeById (Long id);

    Employee update(EmployeeThirdDto employeeDto, Long id);

//
//    Employee updateEmployee (Long id, Employee employeeDetails);
//
//
//    List<Employee> getAllEmployeesByRole(Long roleId);
//
//    Optional<Employee> getEmployeeByKindOfRole(Role role);
//
//    List<Employee> getAllEmployeesByDepartmentName (String departmentName);
//
//    Optional<Employee> getEmployeeByDepartmentNameAndEmployeeId(String departmentName, Employee id);





}
