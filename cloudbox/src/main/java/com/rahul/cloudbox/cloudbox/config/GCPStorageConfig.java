package com.rahul.cloudbox.cloudbox.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class GCPStorageConfig {

    @Value("${gcp.credentials.file:}")
    private String credentialsPath;

    @Value("${gcp.project-id:}")
    private String projectId;

    @Bean
    public Storage googleStorage() throws IOException {
        System.out.println("projectid..."+projectId);
        StorageOptions.Builder optionsBuilder = StorageOptions.newBuilder();

        if (!credentialsPath.isBlank()) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsPath));
            optionsBuilder.setCredentials(credentials);
        }
        if (!projectId.isBlank()) {
            optionsBuilder.setProjectId(projectId);
        }

        return optionsBuilder.build().getService();
    }
//    @Bean
//    public Storage googleStorage() throws IOException {
//        GoogleCredentials credentials = GoogleCredentials
//                .fromStream(new FileInputStream(""));
//
//        return StorageOptions.newBuilder()
//                .setCredentials(credentials)
//                .setProjectId("")
//                .build()
//                .getService();
//    }

}
