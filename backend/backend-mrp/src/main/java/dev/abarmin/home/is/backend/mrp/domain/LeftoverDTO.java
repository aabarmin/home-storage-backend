package dev.abarmin.home.is.backend.mrp.domain;

import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Aleksandr Barmin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
  @Builder.Default
  private LocalDateTime createdAt = LocalDateTime.now();

  /**
   * Amount of a resource.
   */
  @Valid
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
