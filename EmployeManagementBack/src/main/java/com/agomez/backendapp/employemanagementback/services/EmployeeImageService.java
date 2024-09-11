package com.agomez.backendapp.employemanagementback.services;

import com.agomez.backendapp.employemanagementback.dtos.EmployeeDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeImageDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeImageSecondDto;
import com.agomez.backendapp.employemanagementback.entities.EmployeeImage;
import com.agomez.backendapp.employemanagementback.exceptions.FileDownloadException;
import com.agomez.backendapp.employemanagementback.exceptions.FileUploadException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface EmployeeImageService {

    EmployeeImage uploadImage(EmployeeDto employeeDto, EmployeeImage employeeImage) throws FileUploadException, IOException, ParseException;

    void downloadImage(Long id) throws FileDownloadException, IOException;

    List<EmployeeImageDto> findAllImages() throws  IOException;

    CompletableFuture<Void> deleteObjectFromS3Bucket(Long id);

    void setSomeEmployeeImageFieldsAsNull(Long id);

    Optional<EmployeeImageDto> findById(Long id);

    EmployeeImageSecondDto update(Long id, EmployeeDto employeeDto)  throws IOException, FileUploadException;


}
