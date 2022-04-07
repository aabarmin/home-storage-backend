package dev.abarmin.home.is.backend.readings.controller.documents.file;

import java.nio.file.Path;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aleksandr Barmin
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    ImportFileFinder.class
})
class ImportFileFinderTest {
  @Autowired
  ImportFileFinder fileFinder;

  @Value("classpath:/test-data")
  Resource testFolder;

  @Test
  void findFiles() throws Exception {
    final Path folderPath = testFolder.getFile().toPath();
    assertThat(folderPath).isNotNull();

    final Collection<ImportFile> foundFiles = fileFinder.findFiles(
        folderPath,
        "(?<year>\\d{4})(?<month>\\d{2})(?<day>\\d{2}) Test.txt"
    );

    assertThat(foundFiles)
        .isNotNull()
        .hasSize(2);
  }
}