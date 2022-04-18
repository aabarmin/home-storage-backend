package dev.abarmin.home.is.backend.mrp.service;

import dev.abarmin.home.is.backend.mrp.domain.Amount;
import dev.abarmin.home.is.backend.mrp.domain.ConsumptionDTO;
import dev.abarmin.home.is.backend.mrp.domain.LeftoverDTO;
import dev.abarmin.home.is.backend.mrp.domain.RecordCreationType;
import dev.abarmin.home.is.backend.mrp.domain.SupplyDTO;
import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Factory creates leftovers.
 *
 * @author Aleksandr Barmin
 */
@Component
@Validated
public class LeftoverFactory {
  /**
   * Create a leftover of a given supply.
   * @param supplyDTO
   * @return
   */
  public LeftoverDTO calculateLeftover(final @NotNull @Valid SupplyDTO supplyDTO,
                                       final @NotNull LocalDateTime createdAt) {
    return LeftoverDTO.builder()
        .amount(supplyDTO.getAmount().copy())
        .createdAt(createdAt)
        .creationType(RecordCreationType.AUTOMATIC)
        .build();
  }

  public LeftoverDTO calculateLeftover(final @NotNull @Valid SupplyDTO supplyDTO,
                                       final @NotNull @Valid ConsumptionDTO consumptionDTO,
                                       final @NotNull LocalDateTime createdAt) {
    return LeftoverDTO.builder()
        .creationType(RecordCreationType.AUTOMATIC)
        .createdAt(createdAt)
        .amount(supplyDTO.getAmount().minus(consumptionDTO.getAmount()))
        .build();
  }
}
