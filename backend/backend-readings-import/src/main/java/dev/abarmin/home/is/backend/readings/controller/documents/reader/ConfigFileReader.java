package dev.abarmin.home.is.backend.readings.controller.documents.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.home.is.backend.binary.storage.domain.FileInfo;
import dev.abarmin.home.is.backend.binary.storage.service.BinaryService;
import dev.abarmin.home.is.backend.binary.storage.service.BinaryServiceHelper;
import dev.abarmin.home.is.backend.readings.model.ImportConfiguration;
import java.io.InputStream;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
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
  private final BinaryServiceHelper binaryServiceHelper;

  @SneakyThrows
  public ImportConfiguration read(final FileInfo fileInfo) {
    final URL fileUrl = binaryService.download(fileInfo);
    final Resource resource = binaryServiceHelper.downloadToTemporaryFolder(fileUrl);
    try (final InputStream inputStream = resource.getInputStream()) {
      final ImportConfiguration configuration = objectMapper
          .readerFor(ImportConfiguration.class)
          .<ImportConfiguration>readValue(inputStream);
      return configuration;
    }
  }
}
