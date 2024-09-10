package com.agomez.backendapp.employemanagementback.mapstruct;

import com.agomez.backendapp.employemanagementback.dtos.EmployeeImageSecondDto;
import com.agomez.backendapp.employemanagementback.entities.EmployeeImage;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeImageMapper {
    EmployeeImage toEntity(EmployeeImageSecondDto employeeImageSecondDto);

    EmployeeImageSecondDto toDto(EmployeeImage employeeImage);
}