package dev.abarmin.home.is.backend.readings.rest.transformer;

import dev.abarmin.home.is.backend.readings.domain.Device;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.rest.model.DeviceModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Component;

/**
 * @author Aleksandr Barmin
 */
@Mapper(componentModel = "spring")
public interface DeviceTransformer {
  @Mapping(target = "flatId", source = "flat")
  DeviceModel toModel(final Device domain);

  @Mapping(target = "flat", source = "flatId")
  Device toDomain(final DeviceModel model);

  default AggregateReference<Flat, Integer> mapFlat(final int flatId) {
    return AggregateReference.to(flatId);
  }

  default int mapFlatId(final AggregateReference<Flat, Integer> reference) {
    return reference.getId();
  }
}
