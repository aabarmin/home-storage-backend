package dev.abarmin.home.is.backend.binary.storage.repository;

import dev.abarmin.home.is.backend.binary.storage.domain.FileInfo;
import org.mapstruct.Mapper;

/**
 * @author Aleksandr Barmin
 */
@Mapper(componentModel = "spring")
public interface FileInfoTransformer {
  FileInfo toDomain(FileInfoEntity entity);

  FileInfoEntity toEntity(FileInfo domain);
}
