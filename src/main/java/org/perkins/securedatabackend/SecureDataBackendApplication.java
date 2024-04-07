package org.perkins.securedatabackend;

import org.perkins.securedatabackend.file.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@ConfigurationPropertiesScan
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SecureDataBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureDataBackendApplication.class, args);
	}

}
