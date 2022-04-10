package dev.abarmin.home.is.backend.readings.rest.transformer;

import dev.abarmin.home.is.backend.binary.storage.domain.FileInfo;
import dev.abarmin.home.is.backend.readings.rest.model.FileInfoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Aleksandr Barmin
 */
@Mapper(componentModel = "spring")
public interface FileInfoTransformer {
  @Mapping(target = "fileId", source = "id")
  FileInfoModel toModel(FileInfo fileInfo);

  @Mapping(target = "id", source = "fileId")
  FileInfo toDomain(FileInfoModel model);
}
