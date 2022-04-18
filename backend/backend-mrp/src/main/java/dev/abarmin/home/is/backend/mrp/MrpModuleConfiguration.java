package dev.abarmin.home.is.backend.mrp;

import dev.abarmin.home.is.backend.mrp.config.MrpLeftoverCreationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Main configuration for the MRP module.
 *
 * @author Aleksandr Barmin
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties(MrpLeftoverCreationProperties.class)
public class MrpModuleConfiguration {
}
