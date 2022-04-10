package dev.abarmin.home.is.backend.readings.controller.documents.file;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

/**
 * @author Aleksandr Barmin
 */
@Component
public class ImportFileFinder {
  @SneakyThrows
  public Collection<ImportFile> findFiles(final Path basePath, final String fileTemplate) {
    return Files.list(basePath)
        .filter(path -> Files.isRegularFile(path))
        .filter(path -> nameMatches(path, fileTemplate))
        .map(path -> toImportFile(path, fileTemplate))
        .collect(Collectors.toList());
  }

  private static boolean nameMatches(final Path file, final String fileTemplate) {
    final Matcher matcher = Pattern.compile(fileTemplate).matcher(file.getFileName().toString());
    return matcher.matches();
  }

  private static ImportFile toImportFile(final Path file, final String fileTemplate) {
    return new ImportFile(file, toLocalDate(file.getFileName().toString(), fileTemplate));
  }

  private static LocalDate toLocalDate(final String fileName, final String fileTemplate) {
    final Matcher matcher = Pattern.compile(fileTemplate).matcher(fileName);
    if (!matcher.find()) {
      throw new RuntimeException(String.format(
          "Filename %s does not match expression %s",
          fileName,
          fileTemplate
      ));
    }
    final int year = Integer.parseInt(matcher.group("year"));
    final int month = Integer.parseInt(matcher.group("month"));
    final int day = Integer.parseInt(matcher.group("day"));

    return LocalDate.of(year, month, day);
  }
}
