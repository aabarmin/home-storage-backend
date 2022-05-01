package dev.abarmin.home.is.backend.mrp.service;

import com.google.common.collect.Iterables;
import dev.abarmin.home.is.backend.mrp.config.MrpLeftoverCreationProperties;
import dev.abarmin.home.is.backend.mrp.domain.ConsignmentDTO;
import dev.abarmin.home.is.backend.mrp.domain.ConsumptionDTO;
import dev.abarmin.home.is.backend.mrp.domain.LeftoverDTO;
import dev.abarmin.home.is.backend.mrp.domain.ResourceDTO;
import dev.abarmin.home.is.backend.mrp.domain.SupplyDTO;
import dev.abarmin.home.is.backend.mrp.repository.ResourceRepository;
import dev.abarmin.home.is.backend.mrp.validator.ResourceValidator;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Aleksandr Barmin
 */
@Service
@Validated
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
  private final LeftoverFactory leftoverFactory;
  private final MrpLeftoverCreationProperties leftoverProperties;
  private final ResourceRepository resourceRepository;
  private final ConsignmentUpdater consignmentUpdater;
  private final ResourceValidator resourceValidator;

  @Override
  public ResourceDTO createResource(final ResourceDTO resource) {
    /**
     * Validating resource before saving it.
     */
    resourceValidator.validateNewResource(resource);

    /**
     * Starting from a given date in the supply, create leftovers.
     */
    for (ConsignmentDTO consignment : resource.getConsignments()) {
      createAndAddLeftovers(consignment, leftoverProperties.getPlanningHorizon());
    }
    /**
     * Leftovers are created for every day inside the planning horizon, need to save.
     */
    final ResourceDTO savedResource = resourceRepository.save(resource);
    return savedResource;
  }

  @Override
  public ResourceDTO addConsignment(ResourceDTO resourceDTO, ConsignmentDTO consignmentDTO) {
    checkArgument(
        consignmentDTO.getResource() == null,
        "Consignment already associated with another resource"
    );
    /**
     * It is necessary to create leftovers for the given consignment and next add it to the
     * resource.
     */
    createAndAddLeftovers(consignmentDTO, leftoverProperties.getPlanningHorizon());
    resourceDTO.addConsignment(consignmentDTO);
    /**
     * Update other consignments to have coherent data.
     */
    consignmentUpdater.updateConsignments(resourceDTO);
    return resourceRepository.save(resourceDTO);
  }

  @Override
  public ResourceDTO addSupply(final ResourceDTO resourceDTO,
                               final ConsignmentDTO consignmentDTO,
                               final SupplyDTO supplyDTO) {

    /**
     * Checking that consignment belongs to the given resource.
     * Also, it's necessary to check that the given supply does not belong
     * to any resource.
     */
    checkArgument(
        resourceDTO == consignmentDTO.getResource(),
        "Given resource and consignment are not connected"
    );
    checkArgument(
        supplyDTO.getConsignment() == null,
        "Supply should not belong to any resource"
    );
    /**
     * All checks passed, adding supply to consignment.
     */
    consignmentDTO.addSupply(supplyDTO);
    /**
     * Recalculate leftovers.
     */
    consignmentUpdater.updateConsignments(resourceDTO);

    return resourceRepository.save(resourceDTO);
  }

  @Override
  public ResourceDTO addConsumption(final ResourceDTO resourceDTO,
                                    final ConsignmentDTO consignmentDTO,
                                    final ConsumptionDTO consumptionDTO) {
    /**
     * Checking that consignment belongs to the given resource.
     * Also, it's necessary to check that the given supply does not belong
     * to any resource.
     */
    checkArgument(
        resourceDTO == consignmentDTO.getResource(),
        "Given resource and consignment are not connected"
    );
    checkArgument(
        consumptionDTO.getConsignment() == null,
        "Consumption should not belong to any resource"
    );
    /**
     * All checks passed, adding consumption to consignment.
     */
    consignmentDTO.addConsumption(consumptionDTO);
    /**
     * Recalculate leftovers.
     */
    consignmentUpdater.updateConsignments(resourceDTO);

    return resourceRepository.save(resourceDTO);
  }

  /**
   * This function created leftovers starting the first supply.
   *
   * @param consignment
   */
  private void createAndAddLeftovers(final ConsignmentDTO consignment,
                                     final int planningDays) {
    final SupplyDTO firstSupply = Iterables.getOnlyElement(consignment.getSupplies());
    final LocalDateTime firstDate = firstSupply.getCreatedAt();
    for (int i = 0; i < planningDays; i++) {
      final LocalDateTime leftoverCreated = firstDate.plusDays(i);
      final LeftoverDTO newLeftoverDTO = leftoverFactory.calculateLeftover(firstSupply, leftoverCreated);
      consignment.addLeftover(newLeftoverDTO);
    }
  }
}
