package dev.abarmin.home.is.backend.binary.storage.service;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Aleksandr Barmin
 */
@Component
public class BinaryServiceHelper {
  /**
   * Upload a given multipart file to the temporary directory.
   * @param multipartFile to upload.
   * @return path to the temporary directory.
   */
  @SneakyThrows
  public Path uploadToTemporaryFolder(final MultipartFile multipartFile) {
    final String prefix = FilenameUtils.getBaseName(multipartFile.getOriginalFilename());
    final String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
    final Path targetFile = Files.createTempFile(prefix, "." + extension);
    multipartFile.transferTo(targetFile);
    return targetFile;
  }

  /**
   * Download given file to the temporary directory and expose as resource.
   * @param fileUrl
   * @return
   */
  @SneakyThrows
  public Resource downloadToTemporaryFolder(final URL fileUrl) {
    final URLConnection connection = fileUrl.openConnection();
    final Path targetFile = Files.createTempFile("download", ".tmp");
    try (final InputStream contentStream = connection.getInputStream()) {
      Files.copy(contentStream, targetFile, StandardCopyOption.REPLACE_EXISTING);
    }
    return new FileSystemResource(targetFile);
  }
}
