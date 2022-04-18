package dev.abarmin.home.is.backend.mrp.service;

import com.google.common.collect.Iterables;
import dev.abarmin.home.is.backend.mrp.config.MrpLeftoverCreationProperties;
import dev.abarmin.home.is.backend.mrp.domain.ConsignmentDTO;
import dev.abarmin.home.is.backend.mrp.domain.LeftoverDTO;
import dev.abarmin.home.is.backend.mrp.domain.ResourceDTO;
import dev.abarmin.home.is.backend.mrp.domain.SupplyDTO;
import dev.abarmin.home.is.backend.mrp.repository.ResourceRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.google.common.base.Preconditions.*;

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

  @Override
  public ResourceDTO createResource(final ResourceDTO resource) {
    checkArgument(resource.getId() == null, "New resource should not have Id");

    /**
     * A new resource is being created so that it can have multiple consignments but
     * no consumptions of leftovers yet.
     */
    for (ConsignmentDTO consignment : resource.getConsignments()) {
      checkArgument(
          Iterables.isEmpty(consignment.getConsumptions()),
          "There should not be any consumptions");
      checkArgument(
          Iterables.isEmpty(consignment.getLeftovers()),
          "There should not be any leftovers"
      );
    }
    /**
     * Starting from a given date in the supply, create leftovers.
     */
    for (ConsignmentDTO consignment : resource.getConsignments()) {
      final SupplyDTO firstSupply = Iterables.getOnlyElement(consignment.getSupplies());
      final LocalDateTime firstDate = firstSupply.getCreatedAt();
      for (int i = 0; i < leftoverProperties.getPlanningHorizon(); i++) {
        final LocalDateTime leftoverCreated = firstDate.plusDays(i);
        final LeftoverDTO newLeftoverDTO = leftoverFactory.calculateLeftover(firstSupply, leftoverCreated);
        consignment.addLeftover(newLeftoverDTO);
      }
    }
    /**
     * Leftovers are created for every day inside the planning horizon, need to save.
     */
    final ResourceDTO savedResource = resourceRepository.save(resource);
    return savedResource;
  }
}
