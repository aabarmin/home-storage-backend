package dev.abarmin.home.is.backend.readings.controller.documents.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.home.is.backend.readings.domain.FileInfo;
import dev.abarmin.home.is.backend.readings.model.ImportConfiguration;
import dev.abarmin.home.is.backend.readings.service.BinaryService;
import java.io.InputStream;
import java.nio.file.Files;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
    final Resource resource = binaryService.download(fileInfo);
    try (final InputStream inputStream = resource.getInputStream()) {
      final ImportConfiguration configuration = objectMapper
          .readerFor(ImportConfiguration.class)
          .<ImportConfiguration>readValue(inputStream);
      return configuration;
    }
  }
}
