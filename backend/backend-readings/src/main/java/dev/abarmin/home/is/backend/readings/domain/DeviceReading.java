package dev.abarmin.home.is.backend.readings.domain;

import dev.abarmin.home.is.backend.binary.storage.domain.FileInfo;
import java.time.LocalDate;
import java.util.Optional;

/**
 * @author Aleksandr Barmin
 */
public record DeviceReading (
    Integer id,
    Device device,
    Flat flat,
    LocalDate date,
    Optional<Integer> reading,
    Optional<FileInfo> invoiceFile,
    Optional<FileInfo> receiptFile
    ) {

    public DeviceReading {}

    public DeviceReading(Device device,
                         Flat flat,
                         LocalDate date) {

        this(null, device, flat, date, Optional.empty(), Optional.empty(), Optional.empty());
    }

    public DeviceReading withInvoiceFile(final FileInfo invoiceFile) {
        return new DeviceReading(
            this.id(),
            this.device(),
            this.flat(),
            this.date(),
            this.reading(),
            Optional.of(invoiceFile),
            this.receiptFile()
        );
    }

    public DeviceReading withReceiptFile(final FileInfo receiptFile) {
        return new DeviceReading(
            this.id(),
            this.device(),
            this.flat(),
            this.date(),
            this.reading(),
            this.invoiceFile(),
            Optional.of(receiptFile)
        );
    }
}
