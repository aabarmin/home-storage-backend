package dev.abarmin.home.is.backend.readings.domain;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Aleksandr Barmin
 */
@Table("DEVICE_READINGS")
public record DeviceReading (
    @Id @Column("READING_ID") Integer id,
    @Column("READING_DEVICE_ID") AggregateReference<Device, Integer> device,
    @Column("READING_FLAT_ID") AggregateReference<Flat, Integer> flat,
    @Column("READING_YEAR") int year,
    @Column("READING_DATE") LocalDate date,
    @Column("READING_VALUE") Integer reading,
    @Column("READING_INVOICE_FILE_ID") AggregateReference<FileInfo, Integer> invoiceFile,
    @Column("READING_RECEIPT_FILE_ID") AggregateReference<FileInfo, Integer> receiptFile
    ) {
}
