package dev.abarmin.home.is.backend.mrp.domain;

import com.google.common.collect.Lists;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * Information about resource.
 *
 * @author Aleksandr Barmin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"consignments"})
public class ResourceDTO {
  /**
   * Identifier of a resource.
   */
  private Long id;

  /**
   * Name of a resource.
   */
  @Size(max = 255)
  @NotEmpty(message = "Resource should have name")
  private String name;

  /**
   * Collection of consignment associated with the current resource.
   */
  @Valid
  @Size(min = 1, message = "Resource should have at least one consignment")
  private final Collection<ConsignmentDTO> consignments = Lists.newArrayList();

  /**
   * Add a consignment to the resource.
   * @param consignment to be added.
   */
  public void addConsignment(final ConsignmentDTO consignment) {
    consignment.setResource(this);
    consignments.add(consignment);
  }
}
