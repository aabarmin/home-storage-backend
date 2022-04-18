package dev.abarmin.home.is.backend.mrp.domain;

import com.google.common.collect.Sets;
import java.util.Collection;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Resource's consignment.
 *
 * @author Aleksandr Barmin
 */
@Data
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
  @NotEmpty
  @Size(max = 255)
  private String name;

  /**
   * Measure unit used in this consignment.
   */
  @NotNull
  private MeasureUnitDTO measureUnit;

  /**
   * Associated leftovers.
   */
  private final Collection<LeftoverDTO> leftovers = Sets.newTreeSet();

  /**
   * Information about consumption.
   */
  private final Collection<ConsumptionDTO> consumptions = Sets.newTreeSet();

  /**
   * Information about supplies.
   */
  private final Collection<SupplyDTO> supplies = Sets.newTreeSet();
}
