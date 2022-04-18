package dev.abarmin.home.is.backend.mrp.service;

import dev.abarmin.home.is.backend.mrp.domain.ConsignmentDTO;
import dev.abarmin.home.is.backend.mrp.domain.ResourceDTO;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * The service responsible for dealing with resources.
 *
 * @author Aleksandr Barmin
 */
public interface ResourceService {
  /**
   * Create a new resource.
   *
   * @param resource to be created.
   * @throws IllegalArgumentException in case of validation issues
   * @throws javax.validation.ConstraintViolationException in case of validation issues
   * @return
   */
  ResourceDTO createResource(@NotNull(message = "Resource should be provided")
                             @Valid ResourceDTO resource);

  /**
   * Add consignment to resource.
   *
   * @param resourceDTO to be updated.
   * @param consignmentDTO to be added.
   * @throws javax.validation.ConstraintViolationException in case of validation issues.
   * @return updated resource.
   */
  ResourceDTO addConsignment(@NotNull(message = "Resource should be provided")
                             @Valid ResourceDTO resourceDTO,
                             @NotNull(message = "Consignment should be provided")
                             @Valid ConsignmentDTO consignmentDTO);
}
