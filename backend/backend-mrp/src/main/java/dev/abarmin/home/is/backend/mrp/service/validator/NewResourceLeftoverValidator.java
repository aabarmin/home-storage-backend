package dev.abarmin.home.is.backend.mrp.service.validator;

import dev.abarmin.home.is.backend.mrp.domain.ConsignmentDTO;
import dev.abarmin.home.is.backend.mrp.domain.LeftoverDTO;
import dev.abarmin.home.is.backend.mrp.domain.RecordCreationType;
import dev.abarmin.home.is.backend.mrp.domain.ResourceDTO;
import org.springframework.stereotype.Component;

import static com.google.common.base.Preconditions.*;

/**
 * Additional validation of resource leftovers.
 *
 * @author Aleksandr Barmin
 */
@Component
@Deprecated
public class NewResourceLeftoverValidator {
  public void validate(final ResourceDTO resource) {
    for (ConsignmentDTO consignment : resource.getConsignments()) {
      for (LeftoverDTO leftover : consignment.getLeftovers()) {
        checkArgument(leftover.getCreationType() == RecordCreationType.MANUAL,
            "In a new record leftovers should have MANUAL creation type");
      }
    }
  }
}
