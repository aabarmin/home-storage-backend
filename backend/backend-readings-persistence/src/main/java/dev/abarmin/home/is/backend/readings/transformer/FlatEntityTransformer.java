package dev.abarmin.home.is.backend.readings.transformer;

import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.domain.FlatEntity;
import org.mapstruct.Mapper;

/**
 * @author Aleksandr Barmin
 */
@Mapper(componentModel = "spring")
public interface FlatEntityTransformer {
  FlatEntity toEntity(Flat flat);

  Flat toDomain(FlatEntity entity);
}
