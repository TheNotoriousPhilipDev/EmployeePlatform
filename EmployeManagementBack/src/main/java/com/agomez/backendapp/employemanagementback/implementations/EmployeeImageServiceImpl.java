package com.agomez.backendapp.employemanagementback.implementations;

import com.agomez.backendapp.employemanagementback.dtos.EmployeeDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeImageDto;
import com.agomez.backendapp.employemanagementback.dtos.EmployeeImageSecondDto;
import com.agomez.backendapp.employemanagementback.entities.Employee;
import com.agomez.backendapp.employemanagementback.entities.EmployeeImage;
import com.agomez.backendapp.employemanagementback.exceptions.FileDownloadException;
import com.agomez.backendapp.employemanagementback.exceptions.FileUploadException;
import com.agomez.backendapp.employemanagementback.mapstruct.EmployeeImageMapper;
import com.agomez.backendapp.employemanagementback.repositories.EmployeeImageRepository;
import com.agomez.backendapp.employemanagementback.repositories.EmployeeRepository;
import com.agomez.backendapp.employemanagementback.services.EmployeeImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class EmployeeImageServiceImpl implements EmployeeImageService {

    @Value("${aws.bucketName}")
    private String bucketName;

    @Value("${aws.bucketUrl}")
    private String s3urlBucketTemplate;

    private final S3Client s3Client;
    private final S3AsyncClient s3AsyncClient;
    private final EmployeeRepository employeeRepository;
    private final EmployeeImageRepository employeeImageRepository;
    private final EmployeeImageMapper employeeImageMapper;


    @Override
    public EmployeeImage uploadImage(EmployeeDto employeeDto) throws FileUploadException, IOException {
        if (employeeDto.multipartFile().getOriginalFilename().isEmpty()){
            throw new FileUploadException("The name of the file is empty or it doesn't exist");
        }
        String fileName = formattingImageName(employeeDto.multipartFile());
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(employeeDto.multipartFile().getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(employeeDto.multipartFile().getBytes()));

        EmployeeImage employeeImage = new EmployeeImage();
        employeeImage.setCreationDate(new Date());
        employeeImage.setName(fileName);
        employeeImage.setS3ObjectUrl(s3urlBucketTemplate+fileName);
        return employeeImage;
    }


    @Override
    public void downloadImage(Long id) throws FileDownloadException, IOException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new FileDownloadException("Employee with ID: " + id + " Not found."));

        String keyOfTheEmployeeImage = employee.getEmployeeImage().getName();
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(keyOfTheEmployeeImage)
                .build();
        try {
            // Download the file from S3
            byte[] data = s3Client.getObjectAsBytes(getObjectRequest).asByteArray();
            String localFilePath = settingPathForS3objectToBeDownloaded(keyOfTheEmployeeImage);
            try (OutputStream os = new FileOutputStream(localFilePath)) {
                os.write(data);
                System.out.println("File downloaded successfully to " + localFilePath);
            } catch (IOException e) {
                throw new IOException("Error writing file to local path: " + localFilePath, e);
            }
        } catch (S3Exception e) {
            throw new FileDownloadException("Error downloading file from S3: " + e.awsErrorDetails().errorMessage() + e);
        }
    }

    @Override
    public List<EmployeeImageDto> findAllImages(){
        return employeeImageRepository.findAllImages();
    }

    @Override
    public CompletableFuture<Void> deleteObjectFromS3Bucket(Long id) {
        Optional<Employee> employee  = employeeRepository.findById(id);
        if (!employee.isPresent()){
            throw new RuntimeException("Image not found");
        }
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(employee.get().getEmployeeImage().getName())
                .build();
        CompletableFuture<DeleteObjectResponse> response = s3AsyncClient.deleteObject(deleteObjectRequest);
        response.whenComplete((deleteRes, ex) -> {
            if (deleteRes != null){
            }else {
                throw new RuntimeException("An S3 exception occurred during delete", ex);
            }
        });
        return response.thenApply(r -> null);
    }

    @Override
    public void setSomeEmployeeImageFieldsAsNull(Long id){
         Employee employee = employeeRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("Employee with ID:" + id + " Not found"));
         employee.getEmployeeImage().setS3ObjectUrl(null);
         employee.getEmployeeImage().setName(null);
         employee.getEmployeeImage().setCreationDate(null);
         employeeRepository.save(employee);
    }

    @Override
    public EmployeeImageSecondDto update(Long id, EmployeeDto employeeDto) throws IOException, FileUploadException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee with ID: " + id +" Not found"));
        deleteObjectFromS3Bucket(id);
        EmployeeImage employeeImage = employeeImageRepository.findById(employee.getEmployeeImage().getId())
                .orElseThrow(() -> new RuntimeException("There's no image associated to employee with ID: " + id));
        employeeImage = uploadImage(employeeDto);
        employeeImageRepository.save(employeeImage);
        return employeeImageMapper.toDto(employee.getEmployeeImage());
    }


    //This method is used for formatting the name of an image to maintain a standard naming convention when saving images
    public String formattingImageName(MultipartFile multipartFile){
        return multipartFile.getOriginalFilename().toLowerCase().replace(" ", "_");
    }

    public String settingPathForS3objectToBeDownloaded(String filename){
        String userHome= System.getProperty("user.home");
        Locale currentLocale = Locale.getDefault();
        String downloadsFolderLanguage = currentLocale.getLanguage().equals("es") ?"Descargas" : "Downloads";

        Path downloadPath = Paths.get(userHome, downloadsFolderLanguage);
        if (!(Files.exists(downloadPath) && Files.isDirectory(downloadPath))){
            throw new RuntimeException("Error to find Downloads folder");
        }
        Path filePath = downloadPath.resolve(filename);
        return filePath.toString();
    }

}
