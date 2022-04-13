package dev.abarmin.home.is.backend;

import dev.abarmin.home.is.backend.ssm.parameter.SsmParameterStoreApplicationContextInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Aleksandr Barmin
 */
@SpringBootApplication
public class BackendSpringApplication {
  public static void main(String[] args) {
    new SpringApplicationBuilder(BackendSpringApplication.class)
        .initializers(
            new SsmParameterStoreApplicationContextInitializer()
        )
        .build()
        .run(args);
  }
}
