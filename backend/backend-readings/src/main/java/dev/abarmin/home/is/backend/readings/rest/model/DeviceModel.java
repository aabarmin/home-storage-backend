package dev.abarmin.home.is.backend.readings.rest.model;

/**
 * @author Aleksandr Barmin
 */
public record DeviceModel(
    Integer id,
    String title,
    String alias,
    Integer flatId,
    boolean needReadings,
    boolean needInvoices,
    boolean needReceipts
) {
}
