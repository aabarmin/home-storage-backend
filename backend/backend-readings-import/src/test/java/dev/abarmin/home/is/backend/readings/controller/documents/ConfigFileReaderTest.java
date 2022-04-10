package dev.abarmin.home.is.backend.readings.controller.documents;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.home.is.backend.binary.storage.domain.FileInfo;
import dev.abarmin.home.is.backend.binary.storage.service.BinaryService;
import dev.abarmin.home.is.backend.binary.storage.service.BinaryServiceHelper;
import dev.abarmin.home.is.backend.binary.storage.service.FileInfoService;
import dev.abarmin.home.is.backend.readings.controller.documents.reader.ConfigFileReader;
import dev.abarmin.home.is.backend.readings.model.ImportConfiguration;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
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
    BinaryService.class
})
class ConfigFileReaderTest {
  @Autowired
  ConfigFileReader reader;

  @Value("classpath:./import-config-example.json")
  Resource exampleConfigFile;

  @MockBean
  BinaryService binaryService;

  @MockBean
  FileInfoService fileInfoService;

  @MockBean
  BinaryServiceHelper binaryServiceHelper;

  @Test
  void check_contextStarts() {
    assertThat(reader).isNotNull();
  }

  @Test
  void read_configFileIsParsed() throws Exception {
    when(fileInfoService.save(any(FileInfo.class))).thenAnswer(inv -> inv.getArgument(0));
    when(binaryService.upload(any(Path.class))).thenAnswer(inv -> {
      final Path uploadedFile = inv.getArgument(0);
      return new FileInfo(
          uploadedFile.getFileName().toString(),
          Files.probeContentType(uploadedFile),
          uploadedFile.toString()
      );
    });
    when(binaryServiceHelper.uploadToTemporaryFolder(any(MultipartFile.class))).thenCallRealMethod();
    when(binaryService.download(any(FileInfo.class))).thenAnswer(inv -> {
      final FileInfo fileInfo = inv.getArgument(0);
      return new URL("file:///" + fileInfo.filePath());
    });
    when(binaryServiceHelper.downloadToTemporaryFolder(any(URL.class))).thenAnswer(inv -> {
      final URL url = inv.getArgument(0);
      final String file = url.getFile();
      return new FileSystemResource(file);
    });

    final MockMultipartFile mockFile = new MockMultipartFile("file", exampleConfigFile.getInputStream());
    final Path configFile = binaryServiceHelper.uploadToTemporaryFolder(mockFile);
    final FileInfo configFileId = binaryService.upload(configFile);
    final ImportConfiguration result = reader.read(configFileId);

    assertThat(result).isNotNull();
  }
}