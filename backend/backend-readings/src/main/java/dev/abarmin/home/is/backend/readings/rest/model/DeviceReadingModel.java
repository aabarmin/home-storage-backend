package dev.abarmin.home.is.backend.readings.rest.model;

import java.time.LocalDate;

/**
 * @author Aleksandr Barmin
 */
public record DeviceReadingModel(
    Integer id,
    LocalDate date,
    Integer flatId,
    Integer deviceId,
    Integer reading,
    Integer invoiceFileId,
    Integer receiptFileId
) {
}
