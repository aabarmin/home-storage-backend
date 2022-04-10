package dev.abarmin.home.is.backend.binary.storage.repository;

import dev.abarmin.home.is.backend.binary.storage.domain.FileInfo;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Aleksandr Barmin
 */
@Component
@RequiredArgsConstructor
public class FileInfoRepositoryImpl implements FileInfoRepository {
  private final FileInfoJdbcRepository jdbcRepository;
  private final FileInfoEntityTransformer transformer;

  @Override
  public Optional<FileInfo> findById(int id) {
    return jdbcRepository.findById(id)
        .map(transformer::toDomain);
  }

  @Override
  public FileInfo save(FileInfo fileInfo) {
    final FileInfoEntity fileInfoEntity = transformer.toEntity(fileInfo);
    final FileInfoEntity savedEntity = jdbcRepository.save(fileInfoEntity);
    return transformer.toDomain(savedEntity);
  }
}
