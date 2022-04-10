package dev.abarmin.home.is.backend.binary.storage.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import dev.abarmin.home.is.backend.binary.storage.domain.FileInfo;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author Aleksandr Barmin
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BinaryServiceS3Impl implements BinaryService {
  private final AmazonS3 s3Client;
  private final S3ConfigurationProperties properties;
  private final FileInfoService fileInfoService;

  @Override
  @SneakyThrows
  public FileInfo upload(Path path) {
    log.info(
        "Uploading file with name {} to bucket {}",
        path.getFileName().toString(),
        properties.getBucket()
    );

    final PutObjectResult uploadResult = s3Client.putObject(
        properties.getBucket(),
        path.getFileName().toString(),
        path.toFile()
    );

    log.info("Uploaded");

    return fileInfoService.save(new FileInfo(
        path.getFileName().toString(),
        Files.probeContentType(path),
        "s3://" + properties.getBucket() + "/" + path.getFileName().toString()
    ));
  }

  @Override
  public URL download(FileInfo fileInfo) {
    final String objectKey = StringUtils.substringAfter(
        fileInfo.filePath(),
        "s3://" + properties.getBucket() + "/"
    );

    final long expiresAt = Instant.now().plus(
        properties.getValidSeconds(),
        ChronoUnit.SECONDS
    ).toEpochMilli();

    log.info(
        "Generating presigned URL for object {} in bucket {}",
        objectKey,
        properties.getBucket()
    );

    final URL presignedUrl = s3Client.generatePresignedUrl(
        properties.getBucket(),
        objectKey,
        new Date(expiresAt)
    );

    return presignedUrl;
  }
}
