package dev.abarmin.home.is.backend.readings.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Aleksandr Barmin
 */
@Table("devices")
public record DeviceEntity(
    @Id @Column("device_id") Integer id,
    @Column("device_title") String title,
    @Column("device_alias") String alias,
    @Column("device_flat_id") AggregateReference<FlatEntity, Integer> flat,
    @Column("device_need_readings") boolean needReadings,
    @Column("device_need_invoices") boolean needInvoices,
    @Column("device_need_receipts") boolean needReceipts) {
}
