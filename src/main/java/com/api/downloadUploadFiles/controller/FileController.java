package com.api.downloadUploadFiles.controller;

import com.api.downloadUploadFiles.model.FileResponse;
import com.api.downloadUploadFiles.service.IFileSystemStorage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/api")
public class FileController {

    @Autowired
    IFileSystemStorage fileSystemStorage;

    @PostMapping("/uploadfile")
    public ResponseEntity<FileResponse> uploadSingleFile(@RequestParam("file") MultipartFile file) {
        String uploadFile = fileSystemStorage.saveFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/download")
            .path(uploadFile)
            .toUriString();
        return ResponseEntity.status(HttpStatus.OK).body(
            new FileResponse(uploadFile, fileDownloadUri, "File uploaded successfully"));
    }

    @PostMapping("/uploadfiles")
    public ResponseEntity<List<FileResponse>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        List<FileResponse> responses = Arrays
            .asList(files)
            .stream()
            .map(file -> {
                String uploadFile = fileSystemStorage.saveFile(file);
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/download")
                    .path(uploadFile)
                    .toUriString();
                return new FileResponse(uploadFile, fileDownloadUri, "File uploaded successfully");
            }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource resource = fileSystemStorage.loadFile(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


}
