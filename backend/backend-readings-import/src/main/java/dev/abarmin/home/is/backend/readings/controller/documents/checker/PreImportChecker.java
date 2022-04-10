package dev.abarmin.home.is.backend.readings.controller.documents.checker;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import dev.abarmin.home.is.backend.readings.controller.documents.file.ImportFile;
import dev.abarmin.home.is.backend.readings.controller.documents.file.ImportFileFinder;
import dev.abarmin.home.is.backend.readings.model.ConfigurationVisitor;
import dev.abarmin.home.is.backend.readings.model.ImportConfiguration;
import dev.abarmin.home.is.backend.readings.model.ImportSource;
import dev.abarmin.home.is.backend.readings.model.device.ImportDevice;
import dev.abarmin.home.is.backend.readings.model.document.ImportDocument;
import dev.abarmin.home.is.backend.readings.model.flat.ImportFlat;
import dev.abarmin.home.is.backend.readings.service.DeviceService;
import dev.abarmin.home.is.backend.readings.service.FlatService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

/**
 * This component perform per-import checks like:
 * <ul>
 *   <li>check if flats can be created</li>
 *   <li>check if devices can be created</li>
 * </ul>
 *
 * @author Aleksandr Barmin
 */
@Component
@RequiredArgsConstructor
public class PreImportChecker {
  private final FlatService flatService;
  private final DeviceService deviceService;
  private final ImportFileFinder fileFinder;

  /**
   * Check if configuration has errors which does not allow to proceed.
   *
   * @param configuration
   * @return
   */
  public Collection<ValidationMessage> validateConfiguration(final ImportConfiguration configuration) {
    final ValidationVisitor visitor = new ValidationVisitor();
    configuration.accept(visitor);
    return visitor.getMessages();
  }

  public Collection<FlatCheckResult> validateFlats(final ImportConfiguration configuration) {
    return Stream.of(configuration)
        .flatMap(config -> config.getSources().stream())
        .map(source -> source.getFlat())
        .map(flat -> new FlatCheckResult(
            flat.getName(),
            flat.getAlias(),
            flatService.findOneByAlias(flat.getAlias()).isPresent()
        ))
        .collect(Collectors.toList());
  }

  public Collection<DeviceCheckResult> validateDevices(final ImportConfiguration configuration) {
    return Stream.of(configuration)
        .flatMap(config -> config.getSources().stream())
        .flatMap(source -> source.getDevices().stream())
        .map(device -> new DeviceCheckResult(
            device.getName(),
            device.getAlias(),
            deviceService.findByAlias(device.getAlias()).isPresent()
        ))
        .collect(Collectors.toList());
  }

  public Collection<DocumentSourceValidationResult> validateFiles(final ImportConfiguration configuration) {
    return Stream.of(configuration)
        .flatMap(config -> config.getSources().stream())
        .flatMap(source -> source.getDocuments().stream())
        .map(document -> new DocumentSourceValidationResult(
            document.getPath(),
            fileFinder.findFiles(
                Path.of(document.getPath()),
                document.getNamePattern()
            )
        ))
        .collect(Collectors.toList());
  }

  class ValidationVisitor implements ConfigurationVisitor {
    private final Collection<ValidationMessage> messages = Lists.newArrayList();

    public Collection<ValidationMessage> getMessages() {
      return Collections.unmodifiableCollection(messages);
    }

    private void warning(final String message) {
      this.messages.add(ValidationMessage.warning(message));
    }

    private void info(final String message) {
      this.messages.add(ValidationMessage.info(message));
    }

    @Override
    public void accept(ImportConfiguration configuration) {
      if (configuration.getSources().isEmpty()) {
        warning("No sources declared");
      }
    }

    @Override
    public void accept(ImportSource source) {
      if (Iterables.isEmpty(source.getDevices())) {
        warning("No devices declared");
      }
      if (Iterables.isEmpty(source.getDocuments())) {
        warning("No documents declared");
      }
    }

    @Override
    public void accept(ImportFlat importFlat) {
      if (StringUtils.isEmpty(importFlat.getAlias())) {
        warning("Flat alias is not set");
      } else if (flatService.findOneByAlias(importFlat.getAlias()).isPresent()) {
        info(String.format(
            "Flat with alias %s already exists",
            importFlat.getAlias()
        ));
      } else {
        info(String.format(
            "Flat with alias %s does not exist and will be created",
            importFlat.getAlias()
        ));
      }
    }

    @Override
    public void accept(ImportDevice importDevice) {
      if (StringUtils.isEmpty(importDevice.getAlias())) {
        warning("Device alias is not set");
      } else if (deviceService.findByAlias(importDevice.getAlias()).isPresent()) {
        info(String.format(
            "Device with alias %s already exists",
            importDevice.getAlias()
        ));
      } else {
        info(String.format(
            "Device with alias %s does not exist and will be created",
            importDevice.getAlias()
        ));
      }
    }

    @Override
    public void accept(ImportDocument importDocument) {
      if (StringUtils.isEmpty(importDocument.getDeviceAlias())) {
        warning("Device alias for import is not set");
      }

      final Path documentPath = Path.of(importDocument.getPath());
      if (!Files.exists(documentPath)) {
        warning(String.format(
            "Path %s does not exist",
            importDocument.getPath()
        ));
      } else {
        final Collection<ImportFile> importFiles = fileFinder.findFiles(documentPath, importDocument.getNamePattern());
        info(String.format(
            "%s files found for import in path %s and pattern %s",
            importFiles.size(),
            importDocument.getPath(),
            importDocument.getNamePattern()
        ));
      }
    }
  }
}
