package dev.abarmin.home.is.backend.mrp.repository;

import dev.abarmin.home.is.backend.mrp.domain.ResourceDTO;

/**
 * @author Aleksandr Barmin
 */
public interface ResourceRepository {
  /**
   * Save given resource.
   *
   * @param resourceDTO
   * @return
   */
  ResourceDTO save(ResourceDTO resourceDTO);
}
