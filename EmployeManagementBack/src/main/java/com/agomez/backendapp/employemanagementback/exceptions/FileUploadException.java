package com.agomez.backendapp.employemanagementback.exceptions;

public class FileUploadException extends SpringBootFileUploadException{

    public FileUploadException(String message) {
        super(message);
    }
}
