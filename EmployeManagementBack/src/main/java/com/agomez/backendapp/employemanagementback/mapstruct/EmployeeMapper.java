package com.agomez.backendapp.employemanagementback.mapstruct;


import com.agomez.backendapp.employemanagementback.dtos.EmployeeDetailsDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeThirdDto;
import com.agomez.backendapp.employemanagementback.entities.Employee;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {
    EmployeeThirdDto mapEmployeeToDto(Employee employee);

    Employee toEntity(EmployeeDetailsDto employeeDetailsDto);
    EmployeeDetailsDto toDto(Employee employee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Employee partialUpdate(EmployeeDetailsDto employeeDetailsDto, @MappingTarget Employee employee);
}
