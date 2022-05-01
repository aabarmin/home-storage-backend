package dev.abarmin.home.is.backend.mrp.config;

import javax.validation.constraints.Positive;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * Configuration for the MRP resource creation.
 *
 * @author Aleksandr Barmin
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "mrp.leftover")
public class MrpLeftoverCreationProperties {
  @Positive
  private int planningHorizon;
}
