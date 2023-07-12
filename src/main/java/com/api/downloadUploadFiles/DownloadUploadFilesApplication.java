package com.api.downloadUploadFiles;

import com.api.downloadUploadFiles.properties.FileUploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ FileUploadProperties.class })
public class DownloadUploadFilesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DownloadUploadFilesApplication.class, args);
	}

}
