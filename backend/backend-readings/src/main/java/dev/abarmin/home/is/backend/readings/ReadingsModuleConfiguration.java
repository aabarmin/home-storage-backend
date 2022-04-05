package dev.abarmin.home.is.backend.readings;

import dev.abarmin.home.is.backend.readings.config.BinaryStorageProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aleksandr Barmin
 */
@Configuration
@ComponentScan
@EnableConfigurationProperties(BinaryStorageProperties.class)
public class ReadingsModuleConfiguration {
}
