package dev.abarmin.home.is.backend;

import dev.abarmin.home.is.backend.readings.rest.model.DeviceModel;
import dev.abarmin.home.is.backend.readings.rest.model.DeviceReadingModel;
import dev.abarmin.home.is.backend.readings.rest.model.FileInfoModel;
import dev.abarmin.home.is.backend.readings.rest.model.FlatModel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Aleksandr Barmin
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendSpringApplicationRestTest {
  @Autowired
  private RestTemplate restTemplate;

  @LocalServerPort
  private int port;

  @Test
  void happyPath() throws Exception {
    var createdFlat = createFlat();
    var createdDevice = createDevice(createdFlat);
    var createdFile = createFile();
    var readingModel = createReading(createdDevice, createdFile);
  }

  private FileInfoModel createFile() {
    var contentDisposition = ContentDisposition.builder("form-data")
        .name("file")
        .filename("test-file.txt")
        .build();

    var fileMap = new LinkedMultiValueMap<String, String>();
    fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());

    var fileEntity = new HttpEntity<byte[]>("test".getBytes(StandardCharsets.UTF_8), fileMap);

    var requestBody = new LinkedMultiValueMap<String, Object>();
    requestBody.add("file", fileEntity);

    var headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    var httpEntity = new HttpEntity<>(requestBody, headers);

    var createdFile = restTemplate.postForObject(
        "http://localhost:" + port + "/files",
        httpEntity,
        FileInfoModel.class
    );

    return restTemplate.getForObject(
        "http://localhost:" + port + "/files/{id}",
        FileInfoModel.class,
        createdFile.fileId()
    );
  }

  private DeviceReadingModel createReading(final DeviceModel deviceModel,
                                           final FileInfoModel fileModel) {
    var createdReading = restTemplate.postForObject(
        "http://localhost:" + port + "/records",
        new DeviceReadingModel(
            null,
            LocalDate.now(),
            deviceModel.flatId(),
            deviceModel.id(),
            10,
            fileModel.fileId(),
            fileModel.fileId()
        ),
        DeviceReadingModel.class
    );

    assertThat(createdReading)
        .isNotNull();

    return createdReading;
  }

  private DeviceModel createDevice(final FlatModel flatModel) {
    var createdDevice = restTemplate.postForObject(
        "http://localhost:" + port + "/devices",
        new DeviceModel(
            null,
            "Test device",
            "test-device",
            flatModel.id(),
            true,
            true,
            true
        ),
        DeviceModel.class
    );

    assertThat(createdDevice)
        .isNotNull()
        .hasFieldOrProperty("id");

    var retrievedDevice = restTemplate.getForObject(
        "http://localhost:" + port + "/devices/{id}",
        DeviceModel.class,
        createdDevice.id()
    );

    assertThat(retrievedDevice)
        .isNotNull()
        .hasFieldOrProperty("id");

    return retrievedDevice;
  }

  private FlatModel createFlat() {
    var createdFlat = restTemplate.postForObject(
        "http://localhost:" + port + "/flats",
        new FlatModel(
            null,
            "Test flat",
            "test-flat"
        ),
        FlatModel.class
    );

    assertThat(createdFlat)
        .isNotNull()
        .hasFieldOrProperty("id");

    var retrievedFlat = restTemplate.getForObject(
        "http://localhost:" + port + "/flats/{id}",
        FlatModel.class,
        createdFlat.id()
    );

    assertThat(retrievedFlat)
        .isNotNull();

    var allFlats = restTemplate.getForObject(
        "http://localhost:" + port + "/flats",
        FlatModel[].class
    );

    assertThat(allFlats)
        .isNotNull()
        .isNotEmpty();

    return createdFlat;
  }

  @TestConfiguration
  static class Config {
    @Bean
    public RestTemplate restTemplate() {
      return new RestTemplate();
    }
  }
}