package org.perkins.securedatabackend.aws;

public class S3ServiceException extends Exception{
    public S3ServiceException(String message) {
        super(message);
    }

    public S3ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
