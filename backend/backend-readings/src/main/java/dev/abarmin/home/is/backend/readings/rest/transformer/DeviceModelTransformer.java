package dev.abarmin.home.is.backend.readings.rest.transformer;

import dev.abarmin.home.is.backend.readings.domain.Device;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.rest.model.DeviceModel;
import dev.abarmin.home.is.backend.readings.service.FlatService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Aleksandr Barmin
 */
@Mapper(componentModel = "spring")
public abstract class DeviceModelTransformer {
  @Autowired
  private FlatService flatService;

  @Mapping(target = "flatId", source = "flat.id")
  public abstract DeviceModel toModel(final Device domain);

  @Mapping(target = "flat", source = "flatId")
  public abstract Device toDomain(final DeviceModel model);

  protected Flat mapFlat(final int flatId) {
    return flatService.findOneById(flatId)
        .orElseThrow();
  }
}
