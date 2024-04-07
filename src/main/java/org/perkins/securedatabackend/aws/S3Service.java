package org.perkins.securedatabackend.aws;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.perkins.securedatabackend.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.nio.file.Path;
import java.util.UUID;

@Service
public class S3Service {
    private final String bucketName;
    private final Region region;
    private final S3Client s3Client;
    private final Logger logger = LogManager.getLogger();

    @Autowired
    public S3Service(S3Properties properties) throws S3ServiceException {
        if (properties.getBucketName().isEmpty()) {
            throw new S3ServiceException("Zero length bucket name provided");
        }
        bucketName = properties.getBucketName();
        region = properties.getRegion();
        s3Client = S3Client.builder().region(region).build();
    }



    public void upload(Path path) {
        String uuid = UUID.randomUUID().toString().replace("-","");
        String folder = SecurityUtils.getUserName();
        String filePath = path.toString();
        String extension = filePath.substring(filePath.lastIndexOf(".")+1);
        String key = String.format("%s/%s.%s",folder, uuid, extension);
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(key).build();
        s3Client.putObject(putObjectRequest, RequestBody.fromFile(path.toFile()));
    }
}
