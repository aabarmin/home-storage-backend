package dev.abarmin.home.is.backend.readings.controller.documents.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.home.is.backend.binary.storage.domain.FileInfo;
import dev.abarmin.home.is.backend.binary.storage.service.BinaryService;
import dev.abarmin.home.is.backend.readings.model.ImportConfiguration;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

/**
 * This component reads a provided configuration file.
 *
 * @author Aleksandr Barmin
 */
@Component
@RequiredArgsConstructor
public class ConfigFileReader {
  private final ObjectMapper objectMapper;
  private final BinaryService binaryService;

  @SneakyThrows
  public ImportConfiguration read(final FileInfo fileInfo) {
    final Path filePath = binaryService.download(fileInfo);
    try (final InputStream inputStream = Files.newInputStream(filePath)) {
      final ImportConfiguration configuration = objectMapper
          .readerFor(ImportConfiguration.class)
          .<ImportConfiguration>readValue(inputStream);
      return configuration;
    }
  }
}
