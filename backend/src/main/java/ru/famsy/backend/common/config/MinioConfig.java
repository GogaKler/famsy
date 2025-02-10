package ru.famsy.backend.common.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
  @Value("${spring.minio.url}")
  private String minioUrl;
  @Value("${spring.minio.access.name}")
  private String minioAccessKey;
  @Value("${spring.minio.access.secret}")
  private String minioSecretKey;
  @Value("${spring.minio.bucket.name}")
  private String minioBucket;

  @Bean
  public MinioClient minioClient() {
    return MinioClient.builder()
      .endpoint(minioUrl)
      .credentials(minioAccessKey, minioSecretKey)
      .build();
  }
}
