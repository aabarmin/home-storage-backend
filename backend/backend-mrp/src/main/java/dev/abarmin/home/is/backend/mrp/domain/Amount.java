package dev.abarmin.home.is.backend.mrp.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;

import static com.google.common.base.Preconditions.*;

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

    public static Amount of(final MeasureUnitDTO unit) {
        return of(0, unit);
    }

    public static Amount of(final Integer amount, final MeasureUnitDTO unit) {
        return new Amount(
            amount.doubleValue(),
            unit
        );
    }

    /**
     * Sum two amounts.
     * @param member
     * @return
     */
    public Amount plus(final Amount member) {
        checkArgument(
            unit.equals(member.getUnit()),
            "Operation can be performed only on the same units");

        return Amount.builder()
            .unit(unit)
            .amount(Double.sum(amount, member.getAmount()))
            .build();
    }

    /**
     * Substract given amount from the current.
     * @param member
     * @return
     */
    public Amount minus(final Amount member) {
        checkArgument(
            unit.equals(member.getUnit()),
            "Operation can be performed only on the same units");

        return Amount.builder()
            .unit(unit)
            .amount(amount - member.getAmount())
            .build();
    }

    /**
     * Create a new instance of amount.
     * @return
     */
    public Amount copy() {
        return Amount.builder()
            .unit(unit)
            .amount(amount)
            .build();
    }
}
