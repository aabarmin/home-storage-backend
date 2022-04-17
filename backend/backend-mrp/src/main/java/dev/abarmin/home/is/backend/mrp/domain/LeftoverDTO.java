package dev.abarmin.home.is.backend.mrp.domain;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
public class LeftoverDTO {
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
}
