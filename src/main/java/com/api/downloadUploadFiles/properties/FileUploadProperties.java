package com.api.downloadUploadFiles.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

// Spring Boot comes with a handy feature called ConfigurationProperties to help us access all the custom properties
// defined in application.properties file.
// @ConfigurationProperties provides a way to map property files into Java classes!
//@ConfigurationProperties(prefix = “file.upload”) will simply bind all the properties prefixed with file.upload to the corresponding class fields!


@ConfigurationProperties(prefix = "file.upload")
public class FileUploadProperties {
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
