package dev.abarmin.home.is.backend.readings.service;

import dev.abarmin.home.is.backend.readings.config.BinaryStorageProperties;
import dev.abarmin.home.is.backend.readings.domain.FileInfo;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Aleksandr Barmin
 */
@EnableConfigurationProperties(BinaryStorageProperties.class)
@SpringJUnitConfig(classes = {
    BinaryStorageInitializer.class,
    BinaryService.class
})
@TestPropertySource(properties = {
    "storage.base=./test_storage"
})
class BinaryServiceTest {
  @Autowired
  BinaryService binaryService;

  @Autowired
  BinaryStorageInitializer storageInitializer;

  @Autowired
  BinaryStorageProperties storageProperties;

  @MockBean
  FileInfoService infoService;

  @BeforeEach
  void setUp() throws Exception {
    storageInitializer.run(null);
  }

  @AfterEach
  void tearDown() throws Exception {
    final Path basePath = Path.of(storageProperties.getBase());
    if (Files.exists(basePath)) {
      FileUtils.deleteDirectory(basePath.toFile());
    }
  }

  @Test
  void check_contextStarts() {
    assertThat(binaryService).isNotNull();
  }

  @Test
  @DisplayName("Checking if the file can be uploaded")
  void upload_checkIfFileIsUploaded() {
    when(infoService.save(any(FileInfo.class))).thenAnswer(inv -> {
      final FileInfo fileInfo = inv.getArgument(0);
      return fileInfo;
    });

    final MockMultipartFile fileForUpload = new MockMultipartFile(
        "file",
        "original.txt",
        "text/plain",
        "content".getBytes(StandardCharsets.UTF_8));

    final FileInfo fileInfo = binaryService.upload(fileForUpload);

    assertThat(fileInfo)
        .isNotNull()
        .extracting("fileName", "fileType", "filePath")
        .contains("original.txt", "text/plain", "0_original.txt");
  }
}