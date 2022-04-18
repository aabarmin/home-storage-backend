package dev.abarmin.home.is.backend.mrp.domain;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Aleksandr Barmin
 */
@Data
@EqualsAndHashCode(exclude = {"consignment"})
public class LeftoverDTO implements Comparable<LeftoverDTO> {
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
   * A moment when this record was created.
   */
  @NotNull
  private LocalDateTime createdAt = LocalDateTime.now();

  /**
   * Amount of a resource.
   */
  @NotNull
  private Amount amount;

  /**
   * How the record was created.
   */
  @NotNull
  private RecordCreationType creationType;

  @Override
  public int compareTo(LeftoverDTO that) {
    return createdAt.compareTo(that.getCreatedAt());
  }
}
