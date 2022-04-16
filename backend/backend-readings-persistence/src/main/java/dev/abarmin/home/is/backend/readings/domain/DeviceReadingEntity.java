package dev.abarmin.home.is.backend.readings.domain;

import dev.abarmin.home.is.backend.binary.storage.repository.FileInfoEntity;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Aleksandr Barmin
 */
@Table("device_readings")
public record DeviceReadingEntity(
    @Id @Column("reading_id") Integer id,
    @Column("reading_device_id") AggregateReference<DeviceEntity, Integer> device,
    @Column("reading_flat_id") AggregateReference<FlatEntity, Integer> flat,
    @Column("reading_year") int year,
    @Column("reading_date") LocalDate date,
    @Column("reading_value") Integer reading,
    @Column("reading_invoice_file_id") AggregateReference<FileInfoEntity, Integer> invoiceFile,
    @Column("reading_receipt_file_id") AggregateReference<FileInfoEntity, Integer> receiptFile
) {
}
