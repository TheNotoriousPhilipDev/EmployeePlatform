package com.agomez.backendapp.employemanagementback.mapstruct;

import com.agomez.backendapp.employemanagementback.dtos.DepartmentDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeDetailsDto;
import com.agomez.backendapp.employemanagementback.entities.Department;
import com.agomez.backendapp.employemanagementback.entities.Employee;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DepartmentMapper {

    DepartmentDto toDto(Department department);
    List<DepartmentDto> toDtoList(List<Department> departments);

}