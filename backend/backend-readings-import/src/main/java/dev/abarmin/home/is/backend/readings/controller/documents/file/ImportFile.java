package dev.abarmin.home.is.backend.readings.controller.documents.file;

import java.nio.file.Path;
import java.time.LocalDate;

/**
 * @author Aleksandr Barmin
 */
public record ImportFile(
    Path filePath,
    LocalDate date
) {
}
