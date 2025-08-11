package com.spring.objectstore.configs;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioClientConfig {

    @Value("${minio.user}")
    private String user;

    @Value("${minio.password}")
    private String password;

    @Bean
    MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials(user, password)
                .build();
    }
}


