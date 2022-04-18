package dev.abarmin.home.is.backend.mrp.domain;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Supply of a resource in a given consignment.
 *
 * @author Aleksandr Barmin
 */
@Data
@EqualsAndHashCode(exclude = "consignment")
public class SupplyDTO implements Comparable<SupplyDTO> {
  /**
   * Identifier of a record.
   */
  private Long id;

  /**
   * Identifier of a consignment.
   */
  @NotNull
  private ConsignmentDTO consignment;

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

  @Override
  public int compareTo(SupplyDTO that) {
    return createdAt.compareTo(that.getCreatedAt());
  }
}
