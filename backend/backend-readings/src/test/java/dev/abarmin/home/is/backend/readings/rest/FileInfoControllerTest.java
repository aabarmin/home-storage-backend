package dev.abarmin.home.is.backend.readings.rest;

import dev.abarmin.home.is.backend.readings.domain.FileInfo;
import dev.abarmin.home.is.backend.readings.rest.transformer.FileInfoTransformerImpl;
import dev.abarmin.home.is.backend.readings.service.BinaryService;
import dev.abarmin.home.is.backend.readings.service.FileInfoService;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

/**
 * @author Aleksandr Barmin
 */
@ContextConfiguration(classes = {
    FileInfoController.class,
    FileInfoTransformerImpl.class
})
@WebMvcTest(FileInfoController.class)
class FileInfoControllerTest {
  @Autowired
  MockMvc mockMvc;

  @MockBean
  BinaryService binaryService;

  @MockBean
  FileInfoService infoService;

  @Test
  void upload_callsBothUploadServiceAndTransformer() throws Exception {
    when(binaryService.upload(any(MultipartFile.class))).thenReturn(new FileInfo(
        42,
        "file_name.txt",
        "text/plain",
        "some/file_name.txt"
    ));

    mockMvc.perform(
        multipart("/files")
            .file(mockFile())
    );
  }

  MockMultipartFile mockFile() {
    return new MockMultipartFile(
        "file",
        "original_filename.txt",
        MediaType.TEXT_PLAIN.getType(),
        "Content".getBytes(StandardCharsets.UTF_8)
    );
  }
}