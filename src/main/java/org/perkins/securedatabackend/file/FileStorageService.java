package org.perkins.securedatabackend.file;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Logger logger = LogManager.getLogger();
    private final Path rootPath;

    @Autowired
    public FileStorageService(FileStorageProperties properties) throws FileStorageException {
        if (properties.getRootPath().isEmpty()) {
            throw new FileStorageException("Root path not defined (zero length).");
        }
        rootPath = Paths.get(properties.getRootPath());
        logger.info("Initialized FileStorageService.");
    }

    public Path store(MultipartFile file) throws FileStorageException {
        try {
            if (file.isEmpty()) {
                throw new FileStorageException("Cannot store empty file.");
            } else if (file.getOriginalFilename() == null) {
                throw new FileStorageException("Cannot store file with no filename.");
            }
            Path destinationPath = rootPath.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
            // security check
            if (!destinationPath.getParent().equals(rootPath.toAbsolutePath())) {
                throw new FileStorageException("Cannot store file outside of current directory.");
            }
            // ensure the input stream is closed
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            }
            logger.info("Wrote file to: " + destinationPath);
            return destinationPath;
        } catch (IOException e) {
            throw new FileStorageException("Failed to store file.", e);
        }
    }

    public void delete(Path path) throws FileStorageException {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new FileStorageException("Failed to delete file at " + path.toString(), e);
        }
        logger.info("Deleted file found at: " + path.toString());
    }

    @PostConstruct
    public void init() throws FileStorageException {
        if (!Files.exists(rootPath)) {
            try {
                logger.info("Created tmp directory.");
                Files.createDirectories(rootPath);
            } catch (IOException e) {
                throw new FileStorageException("Failed to create uploading directory.");
            }
        } else {
            logger.info("Did not create tmp directory as it already exists.");
        }
    }
}
