package dev.abarmin.home.is.backend.readings.service;

import dev.abarmin.home.is.backend.readings.config.BinaryStorageProperties;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Aleksandr Barmin
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BinaryStorageInitializer implements ApplicationRunner {
  private final BinaryStorageProperties storageProperties;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("Checking if the storage folder exists");
    final Path storageBase = Path.of(storageProperties.getBase());
    if (!Files.exists(storageBase)) {
      log.info("Storage folder does not exist, creating");
      Files.createDirectories(storageBase);
    }
  }
}
