package dev.abarmin.home.is.backend.readings.rest.transformer;

import dev.abarmin.home.is.backend.readings.domain.Device;
import dev.abarmin.home.is.backend.readings.domain.DeviceReading;
import dev.abarmin.home.is.backend.readings.domain.FileInfo;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.rest.model.DeviceReadingModel;
import java.time.LocalDate;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

/**
 * @author Aleksandr Barmin
 */
@Mapper(componentModel = "spring")
public interface DeviceReadingTransformer {
  @Mapping(target = "deviceId", source = "device")
  @Mapping(target = "flatId", source = "flat")
  @Mapping(target = "invoiceFileId", source = "invoiceFile")
  @Mapping(target = "receiptFileId", source = "receiptFile")
  DeviceReadingModel toModel(DeviceReading domain);

  @Mapping(target = "device", source = "deviceId")
  @Mapping(target = "flat", source = "flatId")
  @Mapping(target = "year", source = "date")
  @Mapping(target = "invoiceFile", source = "invoiceFileId")
  @Mapping(target = "receiptFile", source = "receiptFileId")
  DeviceReading toDomain(DeviceReadingModel model);

  default Integer mapReference(final AggregateReference<?, Integer> reference) {
    return Optional.ofNullable(reference)
        .map(AggregateReference::getId)
        .orElse(null);
  }

  default AggregateReference<FileInfo, Integer> mapFile(final Integer fileId) {
    return Optional.ofNullable(fileId)
        .map(value -> AggregateReference.<FileInfo, Integer>to(value))
        .orElse(null);
  }

  default AggregateReference<Device, Integer> mapDevice(final Integer deviceId) {
    return Optional.ofNullable(deviceId)
        .map(value -> AggregateReference.<Device, Integer>to(value))
        .orElse(null);
  }

  default AggregateReference<Flat, Integer> mapFlat(final Integer flatId) {
    return Optional.ofNullable(flatId)
        .map(value -> AggregateReference.<Flat, Integer>to(value))
        .orElse(null);
  }

  default int mapYear(final LocalDate localDate) {
    return localDate.getYear();
  }
}
