package dev.abarmin.home.is.backend.mrp.service;

import dev.abarmin.home.is.backend.mrp.config.MrpLeftoverCreationProperties;
import dev.abarmin.home.is.backend.mrp.domain.Amount;
import dev.abarmin.home.is.backend.mrp.domain.ConsignmentDTO;
import dev.abarmin.home.is.backend.mrp.domain.LeftoverDTO;
import dev.abarmin.home.is.backend.mrp.domain.RecordCreationType;
import dev.abarmin.home.is.backend.mrp.domain.ResourceDTO;
import java.time.LocalDate;
import java.util.Optional;
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
         * Perform updates. As for now, only the simplest one.
         */
        final Optional<LeftoverDTO> leftoverOptional = consignment.getLeftoverCreatedAt(currentDate);
        if (leftoverOptional.isEmpty()) {
          /**
           * No record for the given date, create an empty record.
           */
          final LeftoverDTO emptyLeftover = LeftoverDTO.builder()
              .createdAt(currentDate.atStartOfDay())
              .creationType(RecordCreationType.AUTOMATIC)
              .amount(Amount.of(0, consignment.getMeasureUnit()))
              .build();
          consignment.addLeftover(emptyLeftover);
        } else {
          /**
           * There is a record for the given date but as for now, no calculation is expected.
           * In the future, there should be code which updates the current leftover
           * based on the supply, consumption and the previous manually created points.
           */
        }

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
      final LocalDate leftoverDate = consignment.getFirstLeftover().getCreatedAt().toLocalDate();
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
