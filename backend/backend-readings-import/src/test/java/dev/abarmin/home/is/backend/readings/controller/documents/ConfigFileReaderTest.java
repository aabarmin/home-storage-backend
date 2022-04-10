package dev.abarmin.home.is.backend.readings.controller.documents;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.home.is.backend.readings.config.BinaryStorageProperties;
import dev.abarmin.home.is.backend.readings.controller.documents.reader.ConfigFileReader;
import dev.abarmin.home.is.backend.readings.domain.FileInfo;
import dev.abarmin.home.is.backend.readings.model.ImportConfiguration;
import dev.abarmin.home.is.backend.readings.service.BinaryService;
import dev.abarmin.home.is.backend.readings.service.BinaryStorageInitializer;
import dev.abarmin.home.is.backend.readings.service.FileInfoService;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Aleksandr Barmin
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    ConfigFileReader.class,
    ObjectMapper.class,
    BinaryStorageInitializer.class,
    BinaryService.class
})
@EnableConfigurationProperties(BinaryStorageProperties.class)
@TestPropertySource(properties = {
    "storage.base=./test_storage"
})
class ConfigFileReaderTest {
  @Autowired
  ConfigFileReader reader;

  @Value("classpath:./import-config-example.json")
  Resource exampleConfigFile;

  @Autowired
  BinaryStorageInitializer storageInitializer;

  @Autowired
  BinaryStorageProperties storageProperties;

  @Autowired
  BinaryService binaryService;

  @MockBean
  FileInfoService fileInfoService;

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
    assertThat(reader).isNotNull();
  }

  @Test
  void read_configFileIsParsed() throws Exception {
    when(fileInfoService.save(any(FileInfo.class))).thenAnswer(inv -> inv.getArgument(0));

    final MockMultipartFile mockFile = new MockMultipartFile("file", exampleConfigFile.getInputStream());
    final FileInfo configFileId = binaryService.upload(mockFile);
    final ImportConfiguration result = reader.read(configFileId);

    assertThat(result).isNotNull();
  }
}