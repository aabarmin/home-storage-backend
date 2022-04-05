package dev.abarmin.home.is.backend.readings.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Aleksandr Barmin
 */
@Data
@ConfigurationProperties(prefix = "storage")
public class BinaryStorageProperties {
  @Value("base")
  private String base;
}
