package dev.abarmin.home.is.backend.binary.storage.domain;

/**
 * @author Aleksandr Barmin
 */
public record FileInfo(
    Integer id,
    String fileName,
    String fileType,
    String filePath
) {

  public FileInfo(final String fileName,
                  final String fileType,
                  final String filePath) {
    this(null, fileName, fileType, filePath);
  }
}
