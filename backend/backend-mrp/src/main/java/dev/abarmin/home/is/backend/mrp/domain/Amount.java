package dev.abarmin.home.is.backend.mrp.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Amount value object.
 *
 * @author Aleksandr Barmin
 */
public record Amount(
    /**
     * Amount of a resource.
     */
    @NotNull
    @Size(min = 0)
    Double amount,

    /**
     * Unit of an amount.
     */
    @NotNull
    MeasureUnitDTO unit
) {
}
