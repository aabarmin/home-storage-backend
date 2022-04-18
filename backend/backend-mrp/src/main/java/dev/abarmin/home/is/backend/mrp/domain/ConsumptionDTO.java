package dev.abarmin.home.is.backend.mrp.domain;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Consumption of a resource.
 *
 * @author Aleksandr Barmin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {
    "consignment"
})
public class ConsumptionDTO implements Comparable<ConsumptionDTO> {
  /**
   * Identifier of a record.
   */
  private Long id;

  /**
   * Consignment.
   */
  @NotNull
  private ConsignmentDTO consignment;

  /**
   * Moment when a record was created.
   */
  @NotNull
  @Builder.Default
  private LocalDateTime createdAt = LocalDateTime.now();

  /**
   * Amount of resources consumed.
   */
  @NotNull
  private Amount amount;

  @Override
  public int compareTo(ConsumptionDTO that) {
    return createdAt.compareTo(that.getCreatedAt());
  }
}
