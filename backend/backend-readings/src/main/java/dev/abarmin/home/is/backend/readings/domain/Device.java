package dev.abarmin.home.is.backend.readings.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Aleksandr Barmin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    private Integer id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String alias;

    @NotNull
    private Integer flatId;

    boolean needReadings;
    boolean needInvoices;
    boolean needReceipts;
}
