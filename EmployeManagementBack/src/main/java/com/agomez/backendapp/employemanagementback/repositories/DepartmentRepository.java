package com.agomez.backendapp.employemanagementback.repositories;

import com.agomez.backendapp.employemanagementback.dtos.DepartmentDto;
import com.agomez.backendapp.employemanagementback.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees WHERE d.id = ?1")
    Department findDepartmentDtoById(Long id);

    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees")
    List<Department> findAllCustom();

    List<Department> findAllByName(String name);

}
