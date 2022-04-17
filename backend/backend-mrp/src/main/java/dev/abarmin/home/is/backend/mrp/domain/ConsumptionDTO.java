package dev.abarmin.home.is.backend.mrp.domain;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Consumption of a resource.
 *
 * @author Aleksandr Barmin
 */
@Data
public class ConsumptionDTO {
  /**
   * Identifier of a record.
   */
  private Long id;

  /**
   * Identifier of a consignment.
   */
  @NotNull
  private Long consignmentId;

  /**
   * Moment when a record was created.
   */
  @NotNull
  private LocalDateTime createdAt = LocalDateTime.now();

  /**
   * Amount of resources consumed.
   */
  @NotNull
  private Amount amount;
}
