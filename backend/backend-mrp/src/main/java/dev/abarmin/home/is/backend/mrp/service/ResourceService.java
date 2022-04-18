package dev.abarmin.home.is.backend.mrp.service;

import dev.abarmin.home.is.backend.mrp.domain.ResourceDTO;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * The service responsible for dealing with resources.
 *
 * @author Aleksandr Barmin
 */
public interface ResourceService {
  ResourceDTO createResource(@NotNull(message = "Resource should be provided")
                             @Valid ResourceDTO resource);
}
