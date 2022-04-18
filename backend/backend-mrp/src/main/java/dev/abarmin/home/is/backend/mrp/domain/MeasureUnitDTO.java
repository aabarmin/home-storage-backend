package dev.abarmin.home.is.backend.mrp.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * Measurement unit like kg, pack, bucket, etc.
 *
 * @author Aleksandr Barmin
 */
@Data
public class MeasureUnitDTO {
  /**
   * Name of a unit.
   */
  @NotEmpty
  @Size(max = 255)
  private String name;

  /**
   * Alias of a unit. Should be unique.
   */
  @NotEmpty
  @Size(max = 255)
  private String alias;

  /**
   * Consumption type.
   */
  @NotNull
  private ConsumptionType consumptionType;
}
