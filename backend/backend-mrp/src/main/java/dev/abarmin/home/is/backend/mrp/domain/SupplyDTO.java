package dev.abarmin.home.is.backend.mrp.domain;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Supply of a resource in a given consignment.
 *
 * @author Aleksandr Barmin
 */
@Data
public class SupplyDTO {
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
   * Amount of a resource supplied.
   */
  @NotNull
  private Amount amount;
}
