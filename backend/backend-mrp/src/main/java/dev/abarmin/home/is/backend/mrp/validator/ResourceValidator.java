package dev.abarmin.home.is.backend.mrp.validator;

import com.google.common.collect.Iterables;
import dev.abarmin.home.is.backend.mrp.domain.ConsignmentDTO;
import dev.abarmin.home.is.backend.mrp.domain.ResourceDTO;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * Component that is responsible for resource validation.
 *
 * @author Aleksandr Barmin
 */
@Validated
@Component
@RequiredArgsConstructor
public class ResourceValidator {
  private final ConsignmentValidator consignmentValidator;
  public void validateNewResource(final @Valid ResourceDTO resource) {
    checkArgument(resource.getId() == null, "New resource should not have Id");

    for (ConsignmentDTO consignment : resource.getConsignments()) {
      consignmentValidator.validateNewConsignment(consignment);
    }
  }
}
