package dev.abarmin.home.is.backend.binary.storage.service;

import dev.abarmin.home.is.backend.binary.storage.domain.FileInfo;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Aleksandr Barmin
 */
@Slf4j
@Component
public class BinaryServiceLocalImpl implements BinaryService {
  @Autowired
  private BinaryServiceLocalProperties properties;

  @Autowired
  private FileInfoService fileInfoService;

  @Override
  @SneakyThrows
  public FileInfo upload(final Path sourceFile) {
    log.info("Saving file from {}", sourceFile);

    // check internal folders are created
    var localDate = LocalDate.now();
    var storageFolder = Path.of(properties.getFolder())
        .resolve(String.valueOf(localDate.getYear()))
        .resolve(String.valueOf(localDate.getMonthValue()))
        .resolve(String.valueOf(localDate.getDayOfMonth()));

    if (!Files.exists(storageFolder)) {
      log.info("Creating internal folder {}", storageFolder);
      Files.createDirectories(storageFolder);
    }

    // saving file
    var targetPath = storageFolder.resolve(sourceFile.getFileName());
    Files.copy(sourceFile, targetPath);

    // creating an info object for it
    var internalPath = Path.of(String.valueOf(localDate.getYear()))
        .resolve(String.valueOf(localDate.getMonthValue()))
        .resolve(String.valueOf(localDate.getDayOfMonth()))
        .resolve(sourceFile.getFileName());

    return fileInfoService.save(new FileInfo(
        sourceFile.getFileName().toString(),
        Files.probeContentType(targetPath),
        internalPath.toString()
    ));
  }

  @Override
  public Path download(final FileInfo fileInfo) {
    var targetPath = Path.of(properties.getFolder())
        .resolve(Path.of(fileInfo.filePath()));

    return targetPath;
  }
}
