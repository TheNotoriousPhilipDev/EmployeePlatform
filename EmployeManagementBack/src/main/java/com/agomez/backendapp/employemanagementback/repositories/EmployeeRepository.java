package com.agomez.backendapp.employemanagementback.repositories;

import com.agomez.backendapp.employemanagementback.dtos.EmployeeSecondDto;
import com.agomez.backendapp.employemanagementback.entities.Department;
import com.agomez.backendapp.employemanagementback.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {


@Query("SELECT new com.agomez.backendapp.employemanagementback.dtos.EmployeeSecondDto(" +
        "e.firstName, e.lastName, e.email, e.phoneNumber, e.hireDate) " +
        "FROM Employee e WHERE e.id = ?1")
    Optional<EmployeeSecondDto> findEmployeeById(Long id);

@Query("SELECT new com.agomez.backendapp.employemanagementback.dtos.EmployeeSecondDto(" +
        "e.firstName, e.lastName, e.email, e.phoneNumber, e.hireDate) " +
        "FROM Employee e")
    List<EmployeeSecondDto> findAllEmployees();


}
