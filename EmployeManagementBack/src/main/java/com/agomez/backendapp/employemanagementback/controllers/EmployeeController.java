package com.agomez.backendapp.employemanagementback.controllers;

import com.agomez.backendapp.employemanagementback.dtos.EmployeeDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeSecondDto;
import com.agomez.backendapp.employemanagementback.entities.Employee;
import com.agomez.backendapp.employemanagementback.exceptions.FileUploadException;
import com.agomez.backendapp.employemanagementback.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> uploadEmployee(@ModelAttribute EmployeeDto employeeDto) throws IOException, FileUploadException, ParseException {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.saveEmployee(employeeDto));
   }//checked

   @GetMapping("/{id}")
   public ResponseEntity<?> viewEmployee(@PathVariable Long id){
        Optional<EmployeeSecondDto> optionalEmployee = employeeService.findById(id);
        if (optionalEmployee.isPresent()){
            return ResponseEntity.ok(optionalEmployee.orElseThrow());
        }
        return ResponseEntity.notFound().build();
   }//checked

   @GetMapping
   public List<EmployeeSecondDto> viewEmployees(){
        return employeeService.findAll();
   }//checked


   @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
       if (employeeService.findById(id).isPresent()){
           employeeService.deleteEmployeeById(id);
           return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
       }
       return ResponseEntity.status(HttpStatus.NOT_FOUND)
               .body("Employee with ID: " + id + " is already deleted or doesn't exist");
   }


}
