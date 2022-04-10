package dev.abarmin.home.is.backend.readings.domain;

/**
 * @author Aleksandr Barmin
 */
public record Flat(
    Integer id,
    String title,
    String alias
) {
  public Flat {}

  public Flat(String title,
              String alias) {

    this(null, title, alias);
  }
}
