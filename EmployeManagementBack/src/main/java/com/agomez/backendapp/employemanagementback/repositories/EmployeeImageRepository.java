package com.agomez.backendapp.employemanagementback.repositories;

import com.agomez.backendapp.employemanagementback.dtos.EmployeeImageDto;
import com.agomez.backendapp.employemanagementback.entities.EmployeeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeImageRepository extends JpaRepository<EmployeeImage, Long> {

    @Query("SELECT new com.agomez.backendapp.employemanagementback.dtos.EmployeeImageDto(e.s3ObjectUrl, e.creationDate) FROM EmployeeImage e")
    List<EmployeeImageDto> findAllImages();

    @Query("SELECT new com.agomez.backendapp.employemanagementback.dtos.EmployeeImageDto(e.s3ObjectUrl, e.creationDate) FROM EmployeeImage e WHERE e.id = ?1")
    Optional<EmployeeImageDto> findImageById(Long id);

}