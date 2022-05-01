package dev.abarmin.home.is.backend.mrp.service;

import dev.abarmin.home.is.backend.mrp.config.MrpLeftoverCreationProperties;
import dev.abarmin.home.is.backend.mrp.domain.Amount;
import dev.abarmin.home.is.backend.mrp.domain.ConsignmentDTO;
import dev.abarmin.home.is.backend.mrp.domain.ConsumptionDTO;
import dev.abarmin.home.is.backend.mrp.domain.LeftoverDTO;
import dev.abarmin.home.is.backend.mrp.domain.RecordCreationType;
import dev.abarmin.home.is.backend.mrp.domain.ResourceDTO;
import dev.abarmin.home.is.backend.mrp.domain.SupplyDTO;
import java.time.LocalDate;
import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * This component is responsible for updating leftovers in consignments.
 *
 * @author Aleksandr Barmin
 */
@Validated
@Component
@RequiredArgsConstructor
public class ConsignmentUpdater {
  private final MrpLeftoverCreationProperties leftoverProperties;

  public boolean updateConsignments(final @NotNull @Valid ResourceDTO resourceDTO) {
    final LocalDate periodStart = getStartDate(resourceDTO);
    final LocalDate periodEnd = getEndDate(resourceDTO);

    for (ConsignmentDTO consignment : resourceDTO.getConsignments()) {
      LocalDate currentDate = periodStart;
      while (!currentDate.isEqual(periodEnd)) {
        /**
         * In common, the calculation should be executing in the following way:
         * today = yesterday + (sum(supply) - sum(consume))
         */
        Amount currentValue = consignment.getLeftoverCreatedAt(currentDate.minusDays(1))
            .map(LeftoverDTO::getAmount)
            .orElse(Amount.of(consignment.getMeasureUnit()));

        final Collection<SupplyDTO> allSupplies = consignment.getSupplyCreatedAt(currentDate);
        for (SupplyDTO supply : allSupplies) {
          currentValue = currentValue.plus(supply.getAmount());
        }

        final Collection<ConsumptionDTO> allConsumptions = consignment.getConsumptionCreatedAt(currentDate);
        for (ConsumptionDTO consumption : allConsumptions) {
          currentValue = currentValue.minus(consumption.getAmount());
        }

        final LeftoverDTO emptyLeftover = LeftoverDTO.builder()
            .createdAt(currentDate.atStartOfDay())
            .creationType(RecordCreationType.AUTOMATIC)
            .amount(currentValue)
            .build();
        consignment.addLeftover(emptyLeftover);

        currentDate = currentDate.plusDays(1);
      }
    }

    return false;
  }

  private LocalDate getStartDate(final ResourceDTO resourceDTO) {
    LocalDate date = null;
    for (ConsignmentDTO consignment : resourceDTO.getConsignments()) {
      final LocalDate leftoverDate = consignment.getFirstLeftover().getCreatedAt().toLocalDate();
      if (date == null) {
        date = leftoverDate;
      } else if (leftoverDate.isBefore(date)) {
        date = leftoverDate;
      }
    }
    if (LocalDate.now().isBefore(date)) {
      date = LocalDate.now();
    }
    return date;
  }

  private LocalDate getEndDate(final ResourceDTO resourceDTO) {
    LocalDate date = null;
    for (ConsignmentDTO consignment : resourceDTO.getConsignments()) {
      final LocalDate leftoverDate = consignment.getLastLeftover().getCreatedAt().toLocalDate();
      if (date == null) {
        date = leftoverDate;
      } else if (leftoverDate.isAfter(date)) {
        date = leftoverDate;
      }
    }
    final LocalDate endOfHorizon =
        LocalDate.now().plusDays(leftoverProperties.getPlanningHorizon());
    if (endOfHorizon.isAfter(date)) {
      date = endOfHorizon;
    }
    return date;
  }
}
