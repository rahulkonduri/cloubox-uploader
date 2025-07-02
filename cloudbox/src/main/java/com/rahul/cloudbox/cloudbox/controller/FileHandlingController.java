package com.rahul.cloudbox.cloudbox.controller;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileHandlingController {

    private static final Logger log = LogManager.getLogger(FileHandlingController.class);
    private final Storage storage;

    @Value("${gcs.bucket.name:}")
    private String bucketName;

    public FileHandlingController(Storage storage){
        this.storage=storage;
    }
        @PostMapping("/upload")
        public ResponseEntity<String> handleUpload(@RequestParam("file") MultipartFile file) throws IOException {
            log.info("Tested!!");
            log.info(bucketName);
            try {
                String fileName = file.getOriginalFilename();
                log.info(fileName);
                BlobId blobId = BlobId.of(bucketName, fileName);
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
                storage.create(blobInfo, file.getBytes());

                return ResponseEntity.ok("File uploaded successfully: " + fileName);
            }
            catch (Exception e){
                log.error("error: ", e);
                return ResponseEntity.internalServerError().build();
            }
        }
}
