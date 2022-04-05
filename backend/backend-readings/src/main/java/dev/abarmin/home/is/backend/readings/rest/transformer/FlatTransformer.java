package dev.abarmin.home.is.backend.readings.rest.transformer;

import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.rest.model.FlatModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * @author Aleksandr Barmin
 */
@Mapper(componentModel = "spring")
public interface FlatTransformer {
  FlatModel toModel(Flat domain);

  Flat toDomain(final FlatModel model);
}
