package com.agomez.backendapp.employemanagementback.controllers;

import com.agomez.backendapp.employemanagementback.dtos.EmployeeDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeImageDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeImageSecondDto;
import com.agomez.backendapp.employemanagementback.exceptions.FileUploadException;
import com.agomez.backendapp.employemanagementback.services.EmployeeImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
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

    @PutMapping("/{id}")
    public void deleteImage(@PathVariable Long id){
        employeeImageService.deleteObjectFromS3Bucket(id);
        employeeImageService.setSomeEmployeeImageFieldsAsNull(id);
    }

    @GetMapping
    public List<EmployeeImageDto> viewAll() throws IOException {
        return employeeImageService.findAllImages();
    }

    @PutMapping("/save/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @ModelAttribute EmployeeDto employeeDto) throws FileUploadException, IOException {
        if (employeeDto.multipartFile().isEmpty()){
            throw new FileUploadException("The file is empty or not present");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeImageService.update(id, employeeDto));

    }

}
