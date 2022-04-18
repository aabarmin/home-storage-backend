package dev.abarmin.home.is.backend.mrp.domain;

import com.google.common.collect.Lists;
import java.util.Collection;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Information about resource.
 *
 * @author Aleksandr Barmin
 */
@Data
@EqualsAndHashCode(exclude = {"consignments"})
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

  /**
   * Collection of consignment associated with the current resource.
   */
  private final Collection<ConsignmentDTO> consignments = Lists.newArrayList();
}
