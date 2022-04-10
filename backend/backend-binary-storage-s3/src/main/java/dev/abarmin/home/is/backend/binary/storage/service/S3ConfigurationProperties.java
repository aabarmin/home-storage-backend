package dev.abarmin.home.is.backend.binary.storage.service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author Aleksandr Barmin
 */
@Data
@Validated
@ConfigurationProperties(prefix = "storage")
public class S3ConfigurationProperties {
  @NotEmpty
  private String region;

  @NotEmpty
  private String bucket;

  @Positive
  private int validSeconds;
}
