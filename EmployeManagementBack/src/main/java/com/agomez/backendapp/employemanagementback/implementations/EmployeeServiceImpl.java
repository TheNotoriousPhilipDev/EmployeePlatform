package com.agomez.backendapp.employemanagementback.implementations;

import com.agomez.backendapp.employemanagementback.dtos.EmployeeDetailsDto;
import com.agomez.backendapp.employemanagementback.mapstruct.EmployeeMapper;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeSecondDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeThirdDto;
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
    private final EmployeeMapper employeeMapper;

    @Override
    @Transactional
    public EmployeeDetailsDto saveEmployee(EmployeeDto employeeDto) throws  FileUploadException, IOException, ParseException {

        //format date
        Date date= new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(employeeDto.hireDate());

        //EmployeeImage
        EmployeeImage employeeImage = new EmployeeImage();

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
