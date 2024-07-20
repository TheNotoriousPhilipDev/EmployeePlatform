package com.agomez.backendapp.employemanagementback.repositories;

import com.agomez.backendapp.employemanagementback.entities.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long > {
}
