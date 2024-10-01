package com.agomez.backendapp.employemanagementback.controllers;


import com.agomez.backendapp.employemanagementback.dtos.DepartmentDto;
import com.agomez.backendapp.employemanagementback.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> viewDepartment(@PathVariable Long id){
        DepartmentDto optionalDepartmentDto = departmentService.findDepartmentById(id);
        return ResponseEntity.ok(optionalDepartmentDto);

    }

    @GetMapping()
    public List<DepartmentDto> viewAllDepartments(){
        return departmentService.findAll();

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable Long id, DepartmentDto departmentDto, BindingResult result){
        if (result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.updateDepartment(id, departmentDto));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> delete(@PathVariable String name){
        departmentService.deleteDepartment(name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private ResponseEntity<?> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "The field " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }


}
