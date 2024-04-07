package org.perkins.securedatabackend.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RestController
public class FileUploadController {
    @Autowired
    private FileStorageService fileStorageService;


    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws FileStorageException {
        Path tmpPath = fileStorageService.store(file);
//        fileStorageService.delete(tmpPath);
        return "success";
    }
}
