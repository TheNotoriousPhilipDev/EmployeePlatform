package com.agomez.backendapp.employemanagementback.implementations;

import com.agomez.backendapp.employemanagementback.dtos.DepartmentDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeDto;
import com.agomez.backendapp.employemanagementback.entities.Department;
import com.agomez.backendapp.employemanagementback.entities.Employee;
import com.agomez.backendapp.employemanagementback.mapstruct.DepartmentMapper;
import com.agomez.backendapp.employemanagementback.repositories.DepartmentRepository;
import com.agomez.backendapp.employemanagementback.repositories.EmployeeRepository;
import com.agomez.backendapp.employemanagementback.services.DepartmentService;
import com.agomez.backendapp.employemanagementback.services.EmployeeImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final EmployeeRepository employeeRepository;
    private final EmployeeImageService employeeImageService;

    @Override
    public Department save(Employee employee, EmployeeDto employeeDto) {
        Set<Employee> employees = new HashSet<>();
        employees.add(employee);
        return new Department(employeeDto.departmentName(), employees);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentDto findDepartmentById(Long id) {
        Department department = departmentRepository.findDepartmentDtoById(id);
        return departmentMapper.toDto(department);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDto> findAll() {
        List<Department> departmentList = departmentRepository.findAllCustom();
        return departmentMapper.toDtoList(departmentList);
    }

    @Override
    @Transactional
    public DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(id).orElseThrow();
        department.setName(departmentDto.name().toUpperCase());
        departmentRepository.save(department);
        return departmentMapper.toDto(department);
    }

    @Override
    @Transactional
    public void deleteDepartment(String name) {
        List<Department> departmentList = departmentRepository.findAllByName(name);
        Set<Employee> employeeSet = departmentList.stream()
                        .flatMap(department -> department.getEmployees().stream())
                                .collect(Collectors.toSet());
        for (Employee e: employeeSet){
            employeeImageService.deleteObjectFromS3Bucket(e.getId());
        }
        employeeRepository.deleteEmployeeByDepartmentName(name);
    }


}
