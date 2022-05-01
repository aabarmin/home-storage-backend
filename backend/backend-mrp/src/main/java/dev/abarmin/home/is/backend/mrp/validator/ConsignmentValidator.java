package dev.abarmin.home.is.backend.mrp.validator;

import com.google.common.collect.Iterables;
import dev.abarmin.home.is.backend.mrp.domain.ConsignmentDTO;
import javax.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Aleksandr Barmin
 */
@Validated
@Component
public class ConsignmentValidator {
  public void validateNewConsignment(final @Valid ConsignmentDTO consignment) {
    /**
     * A new resource is being created so that it can have multiple consignments but
     * no consumptions of leftovers yet.
     */
    checkArgument(
        Iterables.isEmpty(consignment.getConsumptions()),
        "There should not be any consumptions");
    checkArgument(
        Iterables.isEmpty(consignment.getLeftovers()),
        "There should not be any leftovers"
    );
    /**
     * New resource should have at least one supply in any consignment.
     */
    checkArgument(
        !Iterables.isEmpty(consignment.getSupplies()),
        "Consignment should have at least one supply"
    );
  }
}
