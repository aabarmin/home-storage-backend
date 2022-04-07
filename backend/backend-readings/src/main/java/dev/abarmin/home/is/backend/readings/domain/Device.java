package dev.abarmin.home.is.backend.readings.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Aleksandr Barmin
 */
@Table("DEVICES")
public record Device(
  @Id @Column("DEVICE_ID") Integer id,
  @Column("DEVICE_TITLE") String title,
  @Column("DEVICE_ALIAS") String alias,
  @Column("DEVICE_FLAT_ID") AggregateReference<Flat, Integer> flat,
  @Column("DEVICE_NEED_READINGS") boolean needReadings,
  @Column("DEVICE_NEED_INVOICES") boolean needInvoices,
  @Column("DEVICE_NEED_RECEIPTS") boolean needReceipts
) {

  @PersistenceConstructor
  public Device {}

  public Device(String title,
                String alias,
                AggregateReference<Flat, Integer> flat,
                boolean needReadings,
                boolean needInvoices,
                boolean needReceipts) {
    this(null, title, alias, flat, needReadings, needInvoices, needReceipts);
  }
}
