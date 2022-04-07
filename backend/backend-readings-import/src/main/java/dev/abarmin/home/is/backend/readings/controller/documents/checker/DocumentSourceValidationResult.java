package dev.abarmin.home.is.backend.readings.controller.documents.checker;

import dev.abarmin.home.is.backend.readings.controller.documents.file.ImportFile;
import java.util.Collection;

/**
 * @author Aleksandr Barmin
 */
public record DocumentSourceValidationResult(
    String path,
    Collection<ImportFile> importableFiles
) {
}
