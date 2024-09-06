package com.agomez.backendapp.employemanagementback.services;

import com.agomez.backendapp.employemanagementback.dtos.EmployeeDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeImageDto;
import com.agomez.backendapp.employemanagementback.entities.EmployeeImage;
import com.agomez.backendapp.employemanagementback.exceptions.FileDownloadException;
import com.agomez.backendapp.employemanagementback.exceptions.FileUploadException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface EmployeeImageService {

    EmployeeImage uploadImage(EmployeeDto employeeDto) throws FileUploadException, IOException, ParseException;

    void downloadImage(Long id) throws FileDownloadException, IOException;

    List<EmployeeImageDto> findAllImages() throws  IOException;


}
