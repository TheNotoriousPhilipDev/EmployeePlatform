package com.agomez.backendapp.employemanagementback.repositories;

import com.agomez.backendapp.employemanagementback.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
