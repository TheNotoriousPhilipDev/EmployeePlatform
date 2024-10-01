package com.agomez.backendapp.employemanagementback.implementations;

import com.agomez.backendapp.employemanagementback.dtos.EmployeeDetailsDto;
import com.agomez.backendapp.employemanagementback.mapstruct.EmployeeMapper;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeSecondDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeThirdDto;
import com.agomez.backendapp.employemanagementback.entities.*;
import com.agomez.backendapp.employemanagementback.enums.KindOfRole;
import com.agomez.backendapp.employemanagementback.exceptions.FileUploadException;
import com.agomez.backendapp.employemanagementback.repositories.EmployeeRepository;
import com.agomez.backendapp.employemanagementback.services.DepartmentService;
import com.agomez.backendapp.employemanagementback.services.EmployeeImageService;
import com.agomez.backendapp.employemanagementback.services.EmployeeService;
import com.agomez.backendapp.employemanagementback.services.EncryptionService;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class EmployeeServiceImpl implements EmployeeService, EncryptionService {

    private final EmployeeImageService employeeImageService;
    private final DepartmentService departmentService;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public EmployeeDetailsDto saveEmployee(EmployeeDto employeeDto) throws  FileUploadException, IOException, ParseException {

        //Employee
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.firstName());
        employee.setLastName(employeeDto.lastName());
        employee.setEmail(employeeDto.email());
        employee.setSalary(BigInteger.valueOf(Integer.parseInt(employeeDto.salary())));
        employee.setPhoneNumber(Long.parseLong(employeeDto.phoneNumber()));
        employee.setHireDate(formatDate(employeeDto));

        //EmployeeImage
        employee.setEmployeeImage(employeeImageService.save(new EmployeeImage(), employeeDto));

        //Department
        employee.setDepartment(departmentService.save(employee, employeeDto));

       //Data structures to be set
        List<Role> roleList = new ArrayList<>();

        //Users
        User user = new User();
        user.setUsername(employeeDto.username());
        user.setPassword(encryptPassword(employeeDto));

        //Role
        Role role = new Role();
        formatEnumsForKindOfRoles(employeeDto, role);
        roleList.add(role);
        user.setRoles(roleList);
        employee.setRole(role);
        user.setEmployee(employee);
        employee.setUser(user);

        log.info("Por aquí pasó");
        employeeRepository.save(employee);
        log.info("Todo ok");
        employeeImageService.uploadImage(employeeDto, employee);
        log.info("se publica la imagen");

        return employeeMapper.toDto(employee);
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
        if (employeeFromDb.get().getEmployeeImage().getS3ObjectUrl() != null){
            employeeImageService.deleteObjectFromS3Bucket(employeeFromDb.get().getEmployeeImage().getId());
        }
        employeeFromDb.ifPresent(employeeRepository::delete);
    }

    @Override
    @Transactional
    public EmployeeThirdDto update(EmployeeThirdDto employeeDto, Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee with ID:" + id + " Not found"));
        employee.setFirstName(employeeDto.firstName());
        employee.setLastName(employeeDto.lastName());
        employee.setEmail(employeeDto.email());
        employee.setPhoneNumber(employeeDto.phoneNumber());
        employeeRepository.save(employee);
        return employeeMapper.mapEmployeeToDto(employee);
    }

    public void formatEnumsForKindOfRoles (EmployeeDto employeeDto, Role role){
        for (KindOfRole k: KindOfRole.values()){
            if (employeeDto.role().equalsIgnoreCase(k.name())){
                role.setKindOfRole(k);
            }
        }
    }

    public Date formatDate(EmployeeDto employeeDto) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(employeeDto.hireDate());
    }


    @Override
    public String encryptPassword(EmployeeDto employeeDto) {
        return passwordEncoder.encode(employeeDto.password());
    }
}
