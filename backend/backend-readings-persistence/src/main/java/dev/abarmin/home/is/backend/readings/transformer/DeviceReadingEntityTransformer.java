package dev.abarmin.home.is.backend.readings.transformer;

import dev.abarmin.home.is.backend.binary.storage.domain.FileInfo;
import dev.abarmin.home.is.backend.binary.storage.repository.FileInfoEntity;
import dev.abarmin.home.is.backend.binary.storage.repository.FileInfoRepository;
import dev.abarmin.home.is.backend.readings.domain.Device;
import dev.abarmin.home.is.backend.readings.domain.DeviceEntity;
import dev.abarmin.home.is.backend.readings.domain.DeviceReading;
import dev.abarmin.home.is.backend.readings.domain.DeviceReadingEntity;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.domain.FlatEntity;
import java.time.LocalDate;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

/**
 * @author Aleksandr Barmin
 */
@Mapper(componentModel = "spring")
public abstract class DeviceReadingEntityTransformer {
  @Autowired
  private FileInfoRepository fileInfoRepository;

  public abstract DeviceReading toDomain(final DeviceReadingEntity entity);

  @Mapping(target = "year", source = "date")
  public abstract DeviceReadingEntity toEntity(final DeviceReading domain);

  protected AggregateReference<DeviceEntity, Integer> mapDevice(final Device device) {
    return AggregateReference.to(device.id());
  }

  protected AggregateReference<FlatEntity, Integer> mapFlat(final Flat flat) {
    return AggregateReference.to(flat.getId());
  }

  protected Integer mapReading(final Optional<Integer> reading) {
    return reading.orElse(null);
  }

  protected Optional<Integer> mapReading(final Integer reading) {
    return Optional.ofNullable(reading);
  }

  protected Optional<FileInfo> mapFile(final AggregateReference<FileInfoEntity, Integer> reference) {
    if (reference == null) {
      return Optional.empty();
    }
    return fileInfoRepository.findById(reference.getId());
  }

  protected AggregateReference<FileInfoEntity, Integer> mapFile(final Optional<FileInfo> fileInfo) {
    return fileInfo.map(info -> AggregateReference.<FileInfoEntity, Integer>to(info.id()))
        .orElse(null);
  }

  protected int mapYear(final LocalDate date) {
    return date.getYear();
  }
}
