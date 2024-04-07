package org.perkins.securedatabackend.file;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("localstorage")
public class FileStorageProperties {
    private String rootPath = "tmp-upload";
    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
}
