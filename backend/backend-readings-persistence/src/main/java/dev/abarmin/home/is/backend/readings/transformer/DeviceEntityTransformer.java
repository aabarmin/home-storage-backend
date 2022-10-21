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
@Mapper(componentModel = "spring")
public abstract class DeviceEntityTransformer {
  @Mapping(source = "flatId", target = "flat")
  public abstract DeviceEntity toEntity(Device device);

  @Mapping(source = "flat", target = "flatId")
  public abstract Device toDomain(DeviceEntity device);

  protected AggregateReference<FlatEntity, Integer> mapFlat(final Integer flat) {
    return AggregateReference.to(flat);
  }

  protected Integer mapFlat(final AggregateReference<FlatEntity, Integer> reference) {
    return reference.getId();
  }
}
