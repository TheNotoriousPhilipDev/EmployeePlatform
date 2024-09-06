package com.agomez.backendapp.employemanagementback.services;

import com.agomez.backendapp.employemanagementback.entities.Department;
import com.agomez.backendapp.employemanagementback.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    Department saveDepartment (Department department);

    Optional<Department> getDepartmentById(Long id);

    List<Department> getAllDepartments();

    Department updateDepartment(Long id, Department departmentDetails);

    Optional<Department> deleteDepartment(Long id);

    Optional<Department> getDepartmentByName(String departmentName);

}
