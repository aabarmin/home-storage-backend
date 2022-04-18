package dev.abarmin.home.is.backend.mrp.service;

import com.google.common.collect.Iterables;
import dev.abarmin.home.is.backend.mrp.config.MrpLeftoverCreationProperties;
import dev.abarmin.home.is.backend.mrp.domain.Amount;
import dev.abarmin.home.is.backend.mrp.domain.ConsignmentDTO;
import dev.abarmin.home.is.backend.mrp.domain.ResourceDTO;
import dev.abarmin.home.is.backend.mrp.domain.SupplyDTO;
import dev.abarmin.home.is.backend.mrp.repository.ResourceRepository;
import java.time.LocalDateTime;
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
import static org.junit.jupiter.api.Assertions.*;
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
    ConsignmentUpdater.class
})
@EnableConfigurationProperties(MrpLeftoverCreationProperties.class)
@TestPropertySource(properties = {
    "mrp.leftover.planning-horizon=10"
})
class ResourceServiceAddConsignmentTest {
  @Autowired
  ResourceService resourceService;

  @MockBean
  ResourceRepository resourceRepository;

  @Test
  void check_contextStarts() {
    assertThat(resourceService).isNotNull();
  }

  @Test
  void addConsignment_shouldReturnUpdatedResource() {
    final ResourceDTO existingResource = createResource();
    final ConsignmentDTO newConsignment = ConsignmentDTO.builder()
        .measureUnit(TestMeasureUnits.kg())
        .name("Pack 2")
        .build();

    newConsignment.addSupply(SupplyDTO.builder()
        .amount(Amount.of(10, TestMeasureUnits.kg()))
        .build());

    final ResourceDTO updatedResource = resourceService.addConsignment(existingResource, newConsignment);

    assertThat(updatedResource)
        .isNotNull();

    assertThat(updatedResource.getConsignments())
        .asList()
        .hasSize(2);

    assertThat(updatedResource.getConsignments())
        .allMatch(consignment -> !Iterables.isEmpty(consignment.getSupplies()))
        .allMatch(consignment -> !Iterables.isEmpty(consignment.getLeftovers()))
        .allMatch(consignment -> Iterables.isEmpty(consignment.getConsumptions()));
  }

  @Test
  void addConsignment_shouldAddLeftoversForDates() {
    final ResourceDTO existingResource = createResource();
    final ConsignmentDTO newConsignment = ConsignmentDTO.builder()
        .measureUnit(TestMeasureUnits.kg())
        .name("Pack 2")
        .build();

    newConsignment.addSupply(SupplyDTO.builder()
        .amount(Amount.of(10, TestMeasureUnits.kg()))
        .createdAt(LocalDateTime.now().minusDays(10))
        .build());

    final ResourceDTO savedResource = resourceService.addConsignment(existingResource, newConsignment);

    assertThat(savedResource.getConsignments())
        .allMatch(consignment -> consignment.getLeftovers().size() == 20);
  }

  private ResourceDTO createResource() {
    when(resourceRepository.save(any(ResourceDTO.class))).thenAnswer(i -> i.getArgument(0));

    final ResourceDTO resource = ResourceDTO.builder()
        .name("Potatoes")
        .build();
    final ConsignmentDTO consignment = ConsignmentDTO.builder()
        .name("Pack 1")
        .measureUnit(TestMeasureUnits.kg())
        .build();
    consignment.addSupply(SupplyDTO.builder()
        .amount(Amount.of(10, TestMeasureUnits.kg()))
        .build());
    resource.addConsignment(consignment);

    return resourceService.createResource(resource);
  }
}