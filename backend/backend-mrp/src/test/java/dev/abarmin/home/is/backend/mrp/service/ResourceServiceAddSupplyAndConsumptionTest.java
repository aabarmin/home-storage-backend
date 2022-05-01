package dev.abarmin.home.is.backend.mrp.service;

import com.google.common.collect.Iterables;
import dev.abarmin.home.is.backend.mrp.config.MrpLeftoverCreationProperties;
import dev.abarmin.home.is.backend.mrp.domain.Amount;
import dev.abarmin.home.is.backend.mrp.domain.ConsignmentDTO;
import dev.abarmin.home.is.backend.mrp.domain.ConsumptionDTO;
import dev.abarmin.home.is.backend.mrp.domain.LeftoverDTO;
import dev.abarmin.home.is.backend.mrp.domain.RecordCreationType;
import dev.abarmin.home.is.backend.mrp.domain.ResourceDTO;
import dev.abarmin.home.is.backend.mrp.domain.SupplyDTO;
import dev.abarmin.home.is.backend.mrp.repository.ResourceRepository;
import dev.abarmin.home.is.backend.mrp.validator.ConsignmentValidator;
import dev.abarmin.home.is.backend.mrp.validator.ResourceValidator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Aleksandr Barmin
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    ResourceServiceImpl.class,
    MethodValidationPostProcessor.class,
    LeftoverFactory.class,
    ConsignmentUpdater.class,
    ResourceValidator.class,
    ConsignmentValidator.class
})
@EnableConfigurationProperties(MrpLeftoverCreationProperties.class)
@TestPropertySource(properties = {
    "mrp.leftover.planning-horizon=10"
})
class ResourceServiceAddSupplyAndConsumptionTest {
  @Autowired
  ResourceService resourceService;

  @MockBean
  ResourceRepository resourceRepository;

  @Test
  @DisplayName("Checking that context starts")
  void check_contextStarts() {
    assertThat(resourceService).isNotNull();
  }

  @Test
  @DisplayName("When a new supply is added, leftovers should be created")
  void addSupply_shouldCreateLeftovers() {
    when(resourceRepository.save(any(ResourceDTO.class))).thenAnswer(i -> i.getArgument(0));

    final ResourceDTO resource = ResourceDTO.builder()
        .name("test resource")
        .build();
    final ConsignmentDTO consignment = ConsignmentDTO.builder()
        .name("Consignment 1")
        .measureUnit(TestMeasureUnits.kg())
        .build();
    consignment.addSupply(SupplyDTO.builder()
        .amount(Amount.of(10, TestMeasureUnits.kg()))
        .build());
    resource.addConsignment(consignment);

    final ResourceDTO createdResource = resourceService.createResource(resource);

    /**
     * Resource should be just saved.
     */
    assertThat(createdResource)
        .withFailMessage("Created resource should be returned")
        .isNotNull();
    /**
     * Check that leftovers are available.
     */
    for (ConsignmentDTO createdConsignment : createdResource.getConsignments()) {
      assertThat(createdConsignment.getLeftovers())
          .withFailMessage("Every consignment should have leftovers")
          .isNotEmpty();

      final Optional<LeftoverDTO> createdLeftoverOptional = createdConsignment.getLeftoverCreatedAt(LocalDate.now());
      assertThat(createdLeftoverOptional.isPresent())
          .withFailMessage("There should be a leftover created today")
          .isTrue();

      final LeftoverDTO createdLeftover = createdLeftoverOptional.get();
      assertThat(createdLeftover)
          .extracting(LeftoverDTO::getAmount, LeftoverDTO::getCreationType)
          .contains(
              Amount.of(10, TestMeasureUnits.kg()),
              RecordCreationType.AUTOMATIC
          );
    }

    final ResourceDTO afterAddingSupply = resourceService.addSupply(
        createdResource,
        consignment,
        SupplyDTO.builder()
            .createdAt(LocalDateTime.now().plusDays(1))
            .amount(Amount.of(5, TestMeasureUnits.kg()))
            .build()
    );

    final ResourceDTO afterAddingConsumption = resourceService.addConsumption(
        afterAddingSupply,
        consignment,
        ConsumptionDTO.builder()
            .amount(Amount.of(10, TestMeasureUnits.kg()))
            .createdAt(LocalDateTime.now().plusDays(2))
            .build()
    );

    final ConsignmentDTO singleConsignment = Iterables.getOnlyElement(afterAddingSupply.getConsignments());

    final LeftoverDTO currentLeftover = singleConsignment.getLeftoverCreatedAt(LocalDate.now()).get();
    final LeftoverDTO tomorrowLeftover = singleConsignment.getLeftoverCreatedAt(LocalDate.now().plusDays(1)).get();
    final LeftoverDTO inDayLeftover = singleConsignment.getLeftoverCreatedAt(LocalDate.now().plusDays(2)).get();

    assertThat(currentLeftover)
        .isNotNull()
        .extracting(LeftoverDTO::getAmount, LeftoverDTO::getCreationType)
        .contains(
            Amount.of(10, TestMeasureUnits.kg()),
            RecordCreationType.AUTOMATIC
        );

    assertThat(tomorrowLeftover)
        .isNotNull()
        .extracting(LeftoverDTO::getAmount, LeftoverDTO::getCreationType)
        .contains(
            Amount.of(15, TestMeasureUnits.kg()),
            RecordCreationType.AUTOMATIC
        );

    assertThat(inDayLeftover)
        .isNotNull()
        .extracting(LeftoverDTO::getAmount, LeftoverDTO::getCreationType)
        .contains(
            Amount.of(5, TestMeasureUnits.kg()),
            RecordCreationType.AUTOMATIC
        );
  }
}