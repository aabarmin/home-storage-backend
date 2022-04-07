package dev.abarmin.home.is.backend.readings.controller.documents.checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.home.is.backend.readings.config.BinaryStorageProperties;
import dev.abarmin.home.is.backend.readings.controller.documents.file.ImportFileFinder;
import dev.abarmin.home.is.backend.readings.controller.documents.reader.ConfigFileReader;
import dev.abarmin.home.is.backend.readings.domain.FileInfo;
import dev.abarmin.home.is.backend.readings.model.ImportConfiguration;
import dev.abarmin.home.is.backend.readings.service.BinaryService;
import dev.abarmin.home.is.backend.readings.service.BinaryStorageInitializer;
import dev.abarmin.home.is.backend.readings.service.DeviceService;
import dev.abarmin.home.is.backend.readings.service.FileInfoService;
import dev.abarmin.home.is.backend.readings.service.FlatService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import lombok.Cleanup;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Aleksandr Barmin
 */
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(BinaryStorageProperties.class)
@ContextConfiguration(classes = {
    PreImportChecker.class,
    ConfigFileReader.class,
    ObjectMapper.class,
    BinaryStorageInitializer.class,
    BinaryService.class
})
@TestPropertySource(properties = {
    "storage.base=./test_storage"
})
class PreImportCheckerTest {
  @Value("classpath:/import-config-example.json")
  Resource exampleConfig;

  @Autowired
  ConfigFileReader configReader;

  @Autowired
  PreImportChecker configChecker;

  @MockBean
  FlatService flatService;

  @MockBean
  DeviceService deviceService;

  @MockBean
  ImportFileFinder fileFinder;

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
  void check_configShouldBeReadAndChecked() throws Exception {
    when(fileInfoService.save(any(FileInfo.class))).thenAnswer(inv -> inv.getArgument(0));

    final MockMultipartFile config = new MockMultipartFile(
        "dummy configuration",
        exampleConfig.getInputStream()
    );
    final FileInfo configFileInfo = binaryService.upload(config);
    final ImportConfiguration configuration = configReader.read(configFileInfo);

    assertThat(configuration).isNotNull();

    final Collection<ValidationMessage> messages = configChecker.validateConfiguration(configuration);

    assertThat(messages).isNotNull();
  }
}