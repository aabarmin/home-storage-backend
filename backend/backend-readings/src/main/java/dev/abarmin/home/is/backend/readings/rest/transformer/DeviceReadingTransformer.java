package dev.abarmin.home.is.backend.readings.rest.transformer;

import dev.abarmin.home.is.backend.binary.storage.domain.FileInfo;
import dev.abarmin.home.is.backend.binary.storage.service.FileInfoService;
import dev.abarmin.home.is.backend.readings.domain.Device;
import dev.abarmin.home.is.backend.readings.domain.DeviceReading;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.rest.model.DeviceReadingModel;
import dev.abarmin.home.is.backend.readings.service.DeviceService;
import dev.abarmin.home.is.backend.readings.service.FlatService;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Aleksandr Barmin
 */
@Mapper(componentModel = "spring")
public abstract class DeviceReadingTransformer {
  @Autowired
  private DeviceService deviceService;

  @Autowired
  private FlatService flatService;

  @Autowired
  private FileInfoService fileInfoService;

  @Mapping(target = "deviceId", source = "device.id")
  @Mapping(target = "flatId", source = "flat.id")
  @Mapping(target = "invoiceFileId", source = "invoiceFile")
  @Mapping(target = "receiptFileId", source = "receiptFile")
  public abstract DeviceReadingModel toModel(DeviceReading domain);

  @Mapping(target = "device", source = "deviceId")
  @Mapping(target = "flat", source = "flatId")
  @Mapping(target = "invoiceFile", source = "invoiceFileId")
  @Mapping(target = "receiptFile", source = "receiptFileId")
  public abstract DeviceReading toDomain(DeviceReadingModel model);

  protected Device mapDevice(final int deviceId) {
    return deviceService.findById(deviceId).orElseThrow();
  }

  protected Flat mapFlat(final int flatId) {
    return flatService.findOneById(flatId).orElseThrow();
  }

  protected Integer mapReading(final Optional<Integer> reading) {
    return reading.map(Integer::intValue)
        .orElse(null);
  }

  protected Optional<Integer> mapReading(final Integer reading) {
    return Optional.ofNullable(reading);
  }

  protected Integer mapFile(final Optional<FileInfo> fileInfo) {
    return fileInfo.map(FileInfo::id)
        .orElse(null);
  }

  protected Optional<FileInfo> mapFile(final Integer fileId) {
    if (fileId == null) {
      return Optional.empty();
    }
    return fileInfoService.findById(fileId);
  }

//  default Integer mapReference(final AggregateReference<?, Integer> reference) {
//    return Optional.ofNullable(reference)
//        .map(AggregateReference::getId)
//        .orElse(null);
//  }
//
//  default AggregateReference<FileInfo, Integer> mapFile(final Integer fileId) {
//    return Optional.ofNullable(fileId)
//        .map(value -> AggregateReference.<FileInfo, Integer>to(value))
//        .orElse(null);
//  }
//
//  default AggregateReference<Device, Integer> mapDevice(final Integer deviceId) {
//    return Optional.ofNullable(deviceId)
//        .map(value -> AggregateReference.<Device, Integer>to(value))
//        .orElse(null);
//  }
//
//  default AggregateReference<Flat, Integer> mapFlat(final Integer flatId) {
//    return Optional.ofNullable(flatId)
//        .map(value -> AggregateReference.<Flat, Integer>to(value))
//        .orElse(null);
//  }
//
}
