package com.agomez.backendapp.employemanagementback.implementations;

import com.agomez.backendapp.employemanagementback.dtos.EmployeeDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeSecondDto;
import com.agomez.backendapp.employemanagementback.entities.*;
import com.agomez.backendapp.employemanagementback.enums.KindOfDepartment;
import com.agomez.backendapp.employemanagementback.enums.KindOfRole;
import com.agomez.backendapp.employemanagementback.exceptions.FileUploadException;
import com.agomez.backendapp.employemanagementback.repositories.EmployeeRepository;
import com.agomez.backendapp.employemanagementback.services.EmployeeImageService;
import com.agomez.backendapp.employemanagementback.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeImageService employeeImageService;

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public Employee saveEmployee(EmployeeDto employeeDto) throws  FileUploadException, IOException, ParseException {

        //formatting the date
        Date date= new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(employeeDto.hireDate());

        //Employee
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.firstName());
        employee.setLastName(employeeDto.lastName());
        employee.setEmail(employeeDto.email());
        employee.setSalary(BigInteger.valueOf(Integer.parseInt(employeeDto.salary())));
        employee.setPhoneNumber(Long.parseLong(employeeDto.phoneNumber()));
        employee.setHireDate(date);
        employee.setEmployeeImage(employeeImageService.uploadImage(employeeDto));

        //Department
        Set<Employee> employeeSet = new HashSet<>();
        employeeSet.add(employee);
        Department department = new Department();
        department.setEmployees(employeeSet);

        formattingEnumsForKindOfDepartment(employeeDto, department);
        employee.setDepartment(department);

        //Users
        List<Role> roleSet = new ArrayList<>();
        Set<User> userSet = new HashSet<>();
        User user = new User();
        user.setUsername(employeeDto.username());
        user.setPassword(employeeDto.password());
        userSet.add(user);

        //Role
        Role role = new Role();
        formattingEnumsForKindOfRoles(employeeDto, role);
        role.setUsers(userSet);
        roleSet.add(role);
        user.setRoles(roleSet);
        employee.setRole(role);
        user.setEmployee(employee);
        employee.setUser(user);

        log.info("Por aquí pasó");
        employeeRepository.save(employee);
        log.info("Todo ok");
        return employee;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeSecondDto> findById(Long id) {
        return employeeRepository.findEmployeeById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeSecondDto> findAll() {
        return employeeRepository.findAllEmployees();
    }

    @Override
    @Transactional
    public void deleteEmployeeById(Long id) {
        Optional<Employee> employeeFromDb = employeeRepository.findById(id);
        employeeFromDb.ifPresent(employeeRepository::delete);

    }

    public void formattingEnumsForKindOfRoles (EmployeeDto employeeDto, Role role){
        for (KindOfRole k: KindOfRole.values()){
            if (employeeDto.role().equalsIgnoreCase(k.name())){
                role.setKindOfRole(k);
            }
        }
    }

    public void formattingEnumsForKindOfDepartment (EmployeeDto employeeDto, Department department){
        for (KindOfDepartment k: KindOfDepartment.values()){
            if (employeeDto.departmentName().equalsIgnoreCase(k.name())){
                department.setName(k);
            }
        }
    }



}
