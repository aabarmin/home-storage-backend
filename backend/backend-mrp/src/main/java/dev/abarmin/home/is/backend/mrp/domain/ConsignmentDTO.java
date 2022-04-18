package dev.abarmin.home.is.backend.mrp.domain;

import com.google.common.collect.Sets;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Resource's consignment.
 *
 * @author Aleksandr Barmin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {
    "resource",
    "leftovers",
    "consumptions",
    "supplies"
})
public class ConsignmentDTO {
  /**
   * Identifier of a consignment.
   */
  private Long id;

  /**
   * Resource.
   */
  @NotNull
  private ResourceDTO resource;

  /**
   * Name of the consignment.
   */
  @NotEmpty(message = "Consignment should have name")
  @Size(max = 255)
  private String name;

  /**
   * Measure unit used in this consignment.
   */
  @NotNull(message = "Consignment should have measure unit")
  private MeasureUnitDTO measureUnit;

  /**
   * Associated leftovers.
   */
  @Valid
  @Size(min = 1, message = "Consignment should have at least one leftover")
  private final Collection<LeftoverDTO> leftovers = Sets.newTreeSet();

  /**
   * Information about consumption.
   */
  @Valid
  private final Collection<ConsumptionDTO> consumptions = Sets.newTreeSet();

  /**
   * Information about supplies.
   */
  @Valid
  private final Collection<SupplyDTO> supplies = Sets.newTreeSet();

  /**
   * Add leftover to the consignment.
   * @param leftoverDTO
   */
  public void addLeftover(final LeftoverDTO leftoverDTO) {
    leftoverDTO.setConsignment(this);
    this.leftovers.add(leftoverDTO);
  }
}
