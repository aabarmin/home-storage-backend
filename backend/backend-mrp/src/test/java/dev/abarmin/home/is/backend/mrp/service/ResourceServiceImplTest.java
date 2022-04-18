package dev.abarmin.home.is.backend.mrp.service;

import dev.abarmin.home.is.backend.mrp.domain.Amount;
import dev.abarmin.home.is.backend.mrp.domain.ConsignmentDTO;
import dev.abarmin.home.is.backend.mrp.domain.ConsumptionType;
import dev.abarmin.home.is.backend.mrp.domain.LeftoverDTO;
import dev.abarmin.home.is.backend.mrp.domain.MeasureUnitDTO;
import dev.abarmin.home.is.backend.mrp.domain.RecordCreationType;
import dev.abarmin.home.is.backend.mrp.domain.ResourceDTO;
import dev.abarmin.home.is.backend.mrp.service.validator.NewResourceLeftoverValidator;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aleksandr Barmin
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    ResourceServiceImpl.class,
    LocalValidatorFactoryBean.class,
    MethodValidationPostProcessor.class,
    NewResourceLeftoverValidator.class
})
class ResourceServiceImplTest {
  @Autowired
  ResourceService resourceService;

  @Test
  @DisplayName("Context should start")
  void check_contextStarts() {
    assertThat(resourceService).isNotNull();
  }

  @Test
  @DisplayName("Resource should be provided and not be null")
  void createResource_resourceShouldBeProvided() {
    assertThatThrownBy(() -> resourceService.createResource(null))
        .isInstanceOf(ConstraintViolationException.class)
        .hasMessageContaining("Resource should be provided");
  }

  @Test
  @DisplayName("Resource should have name")
  void createResource_resourceShouldHaveName() {
    assertThatThrownBy(() -> {
      final ResourceDTO resource = new ResourceDTO();
      resourceService.createResource(resource);
    })
        .isInstanceOf(ConstraintViolationException.class)
        .hasMessageContaining("Resource should have name");
  }

  @Test
  @DisplayName("New resource should not have id")
  void createResource_resourceShouldNotHaveId() {
    assertThatThrownBy(() -> {
      final ResourceDTO resourceDTO = ResourceDTO.builder()
          .name("name")
          .id(10L)
          .build();
      final MeasureUnitDTO measureUnit = MeasureUnitDTO.builder().build();
      final ConsignmentDTO consignmentDTO = ConsignmentDTO.builder()
          .name("Consignment 1")
          .measureUnit(measureUnit)
          .build();
      consignmentDTO.addLeftover(LeftoverDTO.builder()
              .creationType(RecordCreationType.MANUAL)
              .amount(Amount.builder()
                  .amount(10D)
                  .unit(measureUnit)
                  .build())
          .build());
      resourceDTO.addConsignment(consignmentDTO);
      resourceService.createResource(resourceDTO);
    })
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("New resource should not have Id");
  }

  @Test
  @DisplayName("New resource should have at least one consignment")
  void createResource_resourceShouldHaveConsignment() {
    assertThatThrownBy(() -> {
      final ResourceDTO resource = ResourceDTO.builder()
          .name("test resource")
          .build();
      resourceService.createResource(resource);
    })
        .isInstanceOf(ConstraintViolationException.class)
        .hasMessageContaining("Resource should have at least one consignment");
  }

  @Test
  @DisplayName("Resource consignment should have a measure unit")
  void createResource_resourceConsignmentShouldHaveMeasureUnit() {
    assertThatThrownBy(() -> {
      final ResourceDTO resource = ResourceDTO.builder()
          .name("test resource")
          .build();
      resource.addConsignment(ConsignmentDTO.builder().build());
      resourceService.createResource(resource);
    })
        .isInstanceOf(ConstraintViolationException.class)
        .hasMessageContaining("Consignment should have measure unit");
  }

  @Test
  @DisplayName("New resource should have at least one leftover")
  void createResource_resourceConsignmentShouldHaveOneLeftover() {
    assertThatThrownBy(() -> {
      final ResourceDTO resource = ResourceDTO.builder()
          .name("test resource")
          .build();
      final MeasureUnitDTO unitDTO = MeasureUnitDTO.builder()
          .name("kg")
          .alias("kg")
          .consumptionType(ConsumptionType.PARTIAL_CONSUMPTION)
          .build();
      resource.addConsignment(ConsignmentDTO.builder()
          .name("Consignment 1")
          .measureUnit(unitDTO)
          .build());

      resourceService.createResource(resource);
    })
        .isInstanceOf(ConstraintViolationException.class)
        .hasMessageContaining("Consignment should have at least one leftover");
  }

  @Test
  @DisplayName("Resource leftovers should have amount")
  void createResource_resourceLeftoverShouldHaveAmount() {
    assertThatThrownBy(() -> {
      final ResourceDTO resource = ResourceDTO.builder()
          .name("test resource")
          .build();
      final MeasureUnitDTO measureUnit = MeasureUnitDTO.builder()
          .name("kg")
          .alias("kg")
          .consumptionType(ConsumptionType.PARTIAL_CONSUMPTION)
          .build();
      final ConsignmentDTO consignment = ConsignmentDTO.builder()
          .name("Consignment 1")
          .measureUnit(measureUnit)
          .build();
      consignment.addLeftover(LeftoverDTO.builder()
          .amount(Amount.builder()
              .amount(-1.0)
              .unit(measureUnit)
              .build())
          .build());
      resource.addConsignment(consignment);

      resourceService.createResource(resource);
    })
        .isInstanceOf(ConstraintViolationException.class)
        .hasMessageContaining("Amount should be positive");
  }

  @Test
  @DisplayName("When a new resource is created, all leftovers should have manual creation type")
  void createResource_whenNewResourceIsCreatedAllLeftoversShouldHaveManualCreationType() {
    assertThatThrownBy(() -> {
      final ResourceDTO resource = ResourceDTO.builder()
          .name("test resource")
          .build();
      final MeasureUnitDTO measureUnit = MeasureUnitDTO.builder()
          .name("kg")
          .alias("kg")
          .consumptionType(ConsumptionType.PARTIAL_CONSUMPTION)
          .build();
      final ConsignmentDTO consignment = ConsignmentDTO.builder()
          .name("Consignment 1")
          .measureUnit(measureUnit)
          .build();
      consignment.addLeftover(LeftoverDTO.builder()
          .creationType(RecordCreationType.AUTOMATIC)
          .amount(Amount.builder()
              .amount(1.0)
              .unit(measureUnit)
              .build())
          .build());
      resource.addConsignment(consignment);

      resourceService.createResource(resource);
    })
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("In a new record leftovers should have MANUAL creation type");
  }
}