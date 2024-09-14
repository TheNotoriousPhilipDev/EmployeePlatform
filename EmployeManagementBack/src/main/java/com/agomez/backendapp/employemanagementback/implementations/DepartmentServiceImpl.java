package com.agomez.backendapp.employemanagementback.implementations;

import com.agomez.backendapp.employemanagementback.entities.Department;
import com.agomez.backendapp.employemanagementback.entities.Employee;
import com.agomez.backendapp.employemanagementback.repositories.DepartmentRepository;
import com.agomez.backendapp.employemanagementback.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Optional<Department> getDepartmentById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Department> getAllDepartments() {
        return List.of();
    }

    @Override
    public Department updateDepartment(Long id, Department departmentDetails) {
        return null;
    }

    @Override
    public Optional<Department> deleteDepartment(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Department> getDepartmentByName(String departmentName) {
        return Optional.empty();
    }
}
