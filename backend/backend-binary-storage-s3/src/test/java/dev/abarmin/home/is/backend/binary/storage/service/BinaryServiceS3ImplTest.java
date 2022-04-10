package dev.abarmin.home.is.backend.binary.storage.service;

import com.amazonaws.services.s3.AmazonS3;
import dev.abarmin.home.is.backend.binary.storage.domain.FileInfo;
import java.net.URL;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Aleksandr Barmin
 */
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {
    "storage.valid-seconds=60",
    "storage.bucket=test-bucket",
    "storage.region=eu-west-2"
})
@ContextConfiguration(classes = {
    BinaryServiceS3Impl.class
})
@EnableConfigurationProperties(S3ConfigurationProperties.class)
class BinaryServiceS3ImplTest {
  @Autowired
  BinaryServiceS3Impl binaryService;

  @MockBean
  AmazonS3 amazonS3;

  @MockBean
  FileInfoService fileInfoService;

  @Test
  void check_contextStarts() {
    assertThat(binaryService).isNotNull();
  }

  @Test
  void download_checkKeyGeneratedCorrectly() {
    final FileInfo fileInfo = new FileInfo(
        "test.txt",
        "text/plain",
        "s3://test-bucket/test.txt"
    );

    final URL download = binaryService.download(fileInfo);

    final ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
    verify(amazonS3, times(1)).generatePresignedUrl(
        anyString(),
        keyCaptor.capture(),
        any(Date.class)
    );

    final String keyValue = keyCaptor.getValue();

    assertThat(keyValue).isEqualTo("test.txt");
  }
}