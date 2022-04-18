package dev.abarmin.home.is.backend.mrp.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;

/**
 * Amount value object.
 *
 * @author Aleksandr Barmin
 */
@Value
@Builder
public class Amount {
    /**
     * Amount of a resource.
     */
    @NotNull
    @Positive(message = "Amount should be positive")
    private final Double amount;

    /**
     * Unit of an amount.
     */
    @NotNull
    private final MeasureUnitDTO unit;
}
