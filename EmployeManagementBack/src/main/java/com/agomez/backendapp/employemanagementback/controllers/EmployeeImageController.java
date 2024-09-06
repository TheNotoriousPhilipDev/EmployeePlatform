package com.agomez.backendapp.employemanagementback.controllers;

import com.agomez.backendapp.employemanagementback.dtos.EmployeeImageDto;
import com.agomez.backendapp.employemanagementback.services.EmployeeImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/images")
public class EmployeeImageController {

    private final EmployeeImageService employeeImageService;

    @GetMapping("/employees/{id}/downloadEmployeeImage")
    public String downloadEmployeeImage(@PathVariable Long id){
        try {
            employeeImageService.downloadImage(id);
            return "Image downloaded successfully!";
        } catch (Exception e) {
            return "Error downloading image: " + e.getMessage();
        }
    }


    @DeleteMapping("/delete/{id}")
    public void deleteImage(@PathVariable Long id){

    }

    @GetMapping
    public List<EmployeeImageDto> viewAll() throws IOException {
        return employeeImageService.findAllImages();
    }

}
