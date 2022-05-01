package dev.abarmin.home.is.backend.mrp.service;

import dev.abarmin.home.is.backend.mrp.domain.Amount;
import dev.abarmin.home.is.backend.mrp.domain.ConsumptionDTO;
import dev.abarmin.home.is.backend.mrp.domain.LeftoverDTO;
import dev.abarmin.home.is.backend.mrp.domain.RecordCreationType;
import dev.abarmin.home.is.backend.mrp.domain.SupplyDTO;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aleksandr Barmin
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    LeftoverFactory.class
})
class LeftoverFactoryTest {
  @Autowired
  LeftoverFactory factory;

  @Test
  void check_contextStarts() {
    assertThat(factory).isNotNull();
  }

  @Test
  void createLeftover_fromSupply() {
    final Amount amount = Amount.builder()
        .amount(10D)
        .unit(TestMeasureUnits.kg())
        .build();
    final SupplyDTO supplyDTO = SupplyDTO.builder()
        .amount(amount)
        .build();

    final LeftoverDTO leftoverDTO = factory.calculateLeftover(supplyDTO, LocalDateTime.now());

    assertThat(leftoverDTO)
        .isNotNull()
        .extracting(
            LeftoverDTO::getAmount,
            LeftoverDTO::getCreationType
        )
        .contains(
            amount,
            RecordCreationType.AUTOMATIC
        );
  }

  @Test
  void createLeftover_fromSupplyAndConsume() {
    final SupplyDTO supplyDTO = SupplyDTO.builder()
        .amount(Amount.builder()
            .amount(10D)
            .unit(TestMeasureUnits.kg())
            .build())
        .build();

    final ConsumptionDTO consumptionDTO = ConsumptionDTO.builder()
        .amount(Amount.builder()
            .amount(8D)
            .unit(TestMeasureUnits.kg())
            .build())
        .build();

    final LeftoverDTO leftoverDTO = factory.calculateLeftover(
        supplyDTO,
        consumptionDTO,
        LocalDateTime.now()
    );

    assertThat(leftoverDTO)
        .isNotNull()
        .extracting(
            LeftoverDTO::getAmount,
            LeftoverDTO::getCreationType
        )
        .contains(
            Amount.builder().amount(2D).unit(TestMeasureUnits.kg()).build(),
            RecordCreationType.AUTOMATIC
        );
  }
}