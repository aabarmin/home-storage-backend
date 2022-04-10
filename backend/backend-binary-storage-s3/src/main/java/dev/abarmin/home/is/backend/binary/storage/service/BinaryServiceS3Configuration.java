package dev.abarmin.home.is.backend.binary.storage.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aleksandr Barmin
 */
@Configuration
@EnableConfigurationProperties(S3ConfigurationProperties.class)
public class BinaryServiceS3Configuration {
  @Autowired
  private S3ConfigurationProperties properties;

  @Bean
  public AmazonS3 s3Client() {
    return AmazonS3ClientBuilder
        .standard()
        .withRegion(properties.getRegion())
        .build();
  }
}
