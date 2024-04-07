package org.perkins.securedatabackend.file;

import org.perkins.securedatabackend.aws.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RestController
@RequestMapping("/api")
public class FileUploadController {
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private S3Service s3Service;


    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws FileStorageException {
        Path tmpPath = fileStorageService.store(file);
        s3Service.upload(tmpPath);
        fileStorageService.delete(tmpPath);
        return "success";
    }
}
