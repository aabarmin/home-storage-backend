package dev.abarmin.home.is.backend.readings.domain;

/**
 * @author Aleksandr Barmin
 */
public record Device(
    Integer id,
    String title,
    String alias,
    Flat flat,
    boolean needReadings,
    boolean needInvoices,
    boolean needReceipts
) {

  public Device {
  }

  public Device(String title,
                String alias,
                Flat flat,
                boolean needReadings,
                boolean needInvoices,
                boolean needReceipts) {
    this(null, title, alias, flat, needReadings, needInvoices, needReceipts);
  }
}
