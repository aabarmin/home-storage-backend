package dev.abarmin.home.is.backend.mrp.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * Information about resource.
 *
 * @author Aleksandr Barmin
 */
@Data
public class ResourceDTO {
  /**
   * Identifier of a resource.
   */
  private Long id;

  /**
   * Name of a resource.
   */
  @NotEmpty
  @Size(max = 255)
  private String name;
}
