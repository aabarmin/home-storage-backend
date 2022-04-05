package dev.abarmin.home.is.backend.readings.service;

import dev.abarmin.home.is.backend.readings.config.BinaryStorageProperties;
import dev.abarmin.home.is.backend.readings.domain.FileInfo;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Aleksandr Barmin
 */
@Service
@RequiredArgsConstructor
public class BinaryService {
  private final BinaryStorageProperties properties;
  private final FileInfoService fileInfoService;

  @SneakyThrows
  public FileInfo upload(final MultipartFile multipartFile) {
    final Path targetPath = generateFilePath(multipartFile);
    final Path createdPath = Files.createFile(targetPath);
    try (final InputStream inputStream = multipartFile.getInputStream();
         final OutputStream targetStream = Files.newOutputStream(createdPath)) {

      IOUtils.copy(inputStream, targetStream);
    }

    final FileInfo fileInfo = new FileInfo(
        null,
        multipartFile.getOriginalFilename(),
        multipartFile.getContentType(),
        createdPath.getFileName().toString()
    );

    return fileInfoService.save(fileInfo);
  }

  private Path generateFilePath(final MultipartFile multipartFile) {
    int index = 0;
    Path targetPath;
    do {
      targetPath = Path.of(properties.getBase())
          .resolve(index + "_" + multipartFile.getOriginalFilename());
      index++;
    } while (Files.exists(targetPath));
    return targetPath;
  }

  public Resource download(final FileInfo fileInfo) {
    final Path targetPath = Path.of(properties.getBase()).resolve(fileInfo.filePath());
    return new FileSystemResource(targetPath);
  }
}
