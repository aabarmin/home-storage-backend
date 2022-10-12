package dev.abarmin.home.is.backend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author Aleksandr Barmin
 */
@SpringBootApplication
public class BackendSpringApplication {
  public static void main(String[] args) {
    new SpringApplicationBuilder(BackendSpringApplication.class)
        .build()
        .run(args);
  }
}
