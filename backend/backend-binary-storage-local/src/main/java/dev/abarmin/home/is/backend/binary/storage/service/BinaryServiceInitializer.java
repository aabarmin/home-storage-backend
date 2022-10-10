package dev.abarmin.home.is.backend.binary.storage.service;

import java.nio.file.Files;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Aleksandr Barmin
 */
@Slf4j
@Component
public class BinaryServiceInitializer implements ApplicationRunner {
  @Autowired
  private BinaryServiceLocalProperties properties;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    final Path storagePath = Path.of(properties.getFolder());
    if (!Files.exists(storagePath)) {
      log.warn("Directory {} does not exist, creating", storagePath);
      Files.createDirectories(storagePath);
    }
  }
}
