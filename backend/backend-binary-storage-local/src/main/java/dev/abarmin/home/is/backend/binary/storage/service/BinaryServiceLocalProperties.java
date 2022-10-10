package dev.abarmin.home.is.backend.binary.storage.service;

import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * @author Aleksandr Barmin
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "storage.local")
public class BinaryServiceLocalProperties {
  @NotEmpty
  private String folder;
}
