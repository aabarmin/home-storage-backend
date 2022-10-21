package dev.abarmin.home.is.backend.readings.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Aleksandr Barmin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    private Integer id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String alias;

    @Positive
    private int flatId;

    boolean needReadings;
    boolean needInvoices;
    boolean needReceipts;
}
