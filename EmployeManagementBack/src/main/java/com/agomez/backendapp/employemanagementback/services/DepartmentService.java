package com.agomez.backendapp.employemanagementback.services;

import com.agomez.backendapp.employemanagementback.dtos.DepartmentDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeDto;
import com.agomez.backendapp.employemanagementback.entities.Department;
import com.agomez.backendapp.employemanagementback.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    Department save(Employee employee, EmployeeDto employeeDto);

    DepartmentDto findDepartmentById(Long id);

    List<DepartmentDto> findAll();

    DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto);

    void deleteDepartment(String name);


}
