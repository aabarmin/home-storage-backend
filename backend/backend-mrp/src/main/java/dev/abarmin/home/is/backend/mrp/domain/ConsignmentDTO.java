package dev.abarmin.home.is.backend.mrp.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * Resource's consignment.
 *
 * @author Aleksandr Barmin
 */
@Data
public class ConsignmentDTO {
  /**
   * Identifier of a consignment.
   */
  private Long id;

  /**
   * Resource's ID.
   */
  @NotNull
  private Long resourceId;

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
}
