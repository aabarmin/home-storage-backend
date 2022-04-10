package dev.abarmin.home.is.backend.binary.storage.service;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Aleksandr Barmin
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    BinaryServiceHelper.class
})
class BinaryServiceHelperTest {
  @Autowired
  BinaryServiceHelper helper;

  @Test
  void helper_shouldCopyFilesToTemporaryLocation() {
    final MockMultipartFile multipartFile = new MockMultipartFile(
        "file",
        "some_file.txt",
        "text/plain",
        "File Content".getBytes(StandardCharsets.UTF_8)
    );

    final Path uploadedFile = helper.uploadToTemporaryFolder(multipartFile);

    assertThat(uploadedFile)
        .isNotNull();

    assertThat(uploadedFile.getFileName().toString())
        .startsWith("some_file")
        .endsWith(".txt");
  }

  @Test
  void helper_shouldDownloadFiles() throws Exception {
    final URL targetUrl = new URL("https://www.google.com/robots.txt");
    final Resource targetResource = helper.downloadToTemporaryFolder(targetUrl);

    assertThat(targetResource)
        .isNotNull();
  }
}