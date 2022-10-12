package dev.abarmin.home.is.backend.readings.transformer;

import dev.abarmin.home.is.backend.readings.domain.Device;
import dev.abarmin.home.is.backend.readings.domain.DeviceEntity;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.domain.FlatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

/**
 * @author Aleksandr Barmin
 */
@Mapper(componentModel = "spring", uses = FlatEntityTransformer.class)
public abstract class DeviceEntityTransformer {
  public abstract DeviceEntity toEntity(Device device);

  public abstract Device toDomain(DeviceEntity device);

  protected AggregateReference<FlatEntity, Integer> mapFlat(final Flat flat) {
    return AggregateReference.to(flat.getId());
  }
}
