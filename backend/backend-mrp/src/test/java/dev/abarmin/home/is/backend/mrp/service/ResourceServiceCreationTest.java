package dev.abarmin.home.is.backend.mrp.service;

import com.google.common.collect.Iterables;
import dev.abarmin.home.is.backend.mrp.config.MrpLeftoverCreationProperties;
import dev.abarmin.home.is.backend.mrp.domain.Amount;
import dev.abarmin.home.is.backend.mrp.domain.ConsignmentDTO;
import dev.abarmin.home.is.backend.mrp.domain.ConsumptionType;
import dev.abarmin.home.is.backend.mrp.domain.LeftoverDTO;
import dev.abarmin.home.is.backend.mrp.domain.MeasureUnitDTO;
import dev.abarmin.home.is.backend.mrp.domain.RecordCreationType;
import dev.abarmin.home.is.backend.mrp.domain.ResourceDTO;
import dev.abarmin.home.is.backend.mrp.domain.SupplyDTO;
import dev.abarmin.home.is.backend.mrp.repository.ResourceRepository;
import dev.abarmin.home.is.backend.mrp.service.validator.NewResourceLeftoverValidator;
import dev.abarmin.home.is.backend.mrp.validator.ConsignmentValidator;
import dev.abarmin.home.is.backend.mrp.validator.ResourceValidator;
import java.util.Collection;
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
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Aleksandr Barmin
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    ResourceServiceImpl.class,
    ConsignmentUpdater.class,
    MethodValidationPostProcessor.class,
    LeftoverFactory.class,
    ResourceValidator.class,
    ConsignmentValidator.class
})
@EnableConfigurationProperties(MrpLeftoverCreationProperties.class)
@TestPropertySource(properties = {
    "mrp.leftover.planning-horizon=10"
})
class ResourceServiceCreationTest {
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
  @DisplayName("Should return created resource with leftovers")
  void createResource_shouldReturnCreatedResourceWithLeftovers() {
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

    assertThat(createdResource)
        .isNotNull()
        .withFailMessage("Created resource was not returned");

    final Collection<LeftoverDTO> leftovers = Iterables.getOnlyElement(createdResource.getConsignments()).getLeftovers();
    assertThat(leftovers)
        .isNotNull()
        .hasSize(10)
        .withFailMessage("There should be more than one leftover");

    assertThat(leftovers)
        .allMatch(leftover -> leftover.getConsignment() == consignment)
        .allMatch(leftover -> leftover.getAmount().equals(
            Amount.of(10, TestMeasureUnits.kg())
        ))
        .allMatch(leftover -> leftover.getCreationType() == RecordCreationType.AUTOMATIC)
        .allMatch(leftover -> leftover.getCreatedAt() != null);
  }
}