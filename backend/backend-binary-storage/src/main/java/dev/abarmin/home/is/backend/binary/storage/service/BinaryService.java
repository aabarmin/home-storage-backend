package dev.abarmin.home.is.backend.binary.storage.service;

import dev.abarmin.home.is.backend.binary.storage.domain.FileInfo;
import java.net.URL;
import java.nio.file.Path;

/**
 * The service to deal with binary files.
 *
 * @author Aleksandr Barmin
 */
public interface BinaryService {
  /**
   * Upload a binary file from the given location.
   * @param path of the file to upload.
   * @return an information about uploaded file.
   */
  FileInfo upload(Path path);

  /**
   * Get a URL to download a file.
   * @param fileInfo with information about file to download.
   * @return an URL for download.
   */
  URL download(FileInfo fileInfo);
}
