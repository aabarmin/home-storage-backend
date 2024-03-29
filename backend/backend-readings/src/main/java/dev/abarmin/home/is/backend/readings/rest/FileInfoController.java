package dev.abarmin.home.is.backend.readings.rest;

import dev.abarmin.home.is.backend.binary.storage.domain.FileInfo;
import dev.abarmin.home.is.backend.binary.storage.service.BinaryService;
import dev.abarmin.home.is.backend.binary.storage.service.BinaryServiceHelper;
import dev.abarmin.home.is.backend.binary.storage.service.FileInfoService;
import dev.abarmin.home.is.backend.readings.rest.model.FileInfoModel;
import dev.abarmin.home.is.backend.readings.rest.transformer.FileInfoTransformer;
import java.net.URL;
import java.net.http.HttpHeaders;
import java.nio.file.Path;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Aleksandr Barmin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileInfoController {
  private final BinaryServiceHelper binaryServiceHelper;
  private final BinaryService binaryService;
  private final FileInfoService fileInfoService;
  private final FileInfoTransformer transformer;

  @PostMapping
  public FileInfoModel upload(final @RequestParam("file") MultipartFile file) {
    final Path filePath = binaryServiceHelper.uploadToTemporaryFolder(file);
    return Optional.of(binaryService.upload(filePath))
        .map(transformer::toModel)
        .get();
  }

  @GetMapping("/{id}")
  public FileInfoModel findOne(final @PathVariable("id") int id) {
    return fileInfoService.findById(id)
        .map(transformer::toModel)
        .orElseThrow();
  }

  @GetMapping("/{id}/download")
  public ResponseEntity<?> download(final @PathVariable("id") int id) {
    final FileInfo fileInfo = fileInfoService.findById(id)
        .orElseThrow();

    final URL downloadUrl = binaryService.download(fileInfo);
    return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
        .header("Location", downloadUrl.toString())
        .build();
  }
}
