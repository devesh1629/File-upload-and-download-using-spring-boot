package com.api.downloadUploadFiles.service;

import com.api.downloadUploadFiles.exception.FileNotFoundException;
import com.api.downloadUploadFiles.exception.FileStorageException;
import com.api.downloadUploadFiles.properties.FileUploadProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Log4j2
@Service
public class FileSystemStorageService implements IFileSystemStorage {

    private final Path dirLocation;
    @Autowired
    public FileSystemStorageService(FileUploadProperties fileUploadProperties) {
        this.dirLocation = Paths.get(fileUploadProperties.getLocation())
                                .toAbsolutePath()
                                .normalize();
    }

    @Override
    @PostConstruct
    public void init() {

    }

    @Override
    public String saveFile(MultipartFile file) {
        log.info(this.dirLocation);
        try {
            String fileName = file.getOriginalFilename();
            Path dFile = this.dirLocation.resolve(fileName);
            Files.copy(file.getInputStream(), dFile, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }
        catch (Exception e) {
            throw new FileStorageException("Could not upload file");
        }
    }

    @Override
    public Resource loadFile(String fileName) {
        try {
            Path file = this.dirLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new FileNotFoundException("Could not find file");
            }
        }
        catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not download file");
        }
    }
}
