package dev.abarmin.home.is.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Aleksandr Barmin
 */
@SpringBootApplication
public class BackendSpringApplication {
  public static void main(String[] args) {
    SpringApplication.run(BackendSpringApplication.class, args);
  }
}
