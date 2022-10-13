package dev.abarmin.home.is.backend.readings.rest.model;

/**
 * @author Aleksandr Barmin
 */
public record FlatModel(
    Integer id,
    String title,
    String alias
) {

  public FlatModel() {
    this(null, "", "");
  }
}
