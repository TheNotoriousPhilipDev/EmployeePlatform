package com.agomez.backendapp.employemanagementback.exceptions;

public class FileDownloadException extends SpringBootFileUploadException{

    public FileDownloadException(String message) {
        super(message);
    }
}
