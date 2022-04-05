package dev.abarmin.home.is.backend.readings.rest.model;

/**
 * @author Aleksandr Barmin
 */
public record FileInfoModel(
    Integer fileId,
    String fileName,
    String fileType,
    String filePath
) {
}
