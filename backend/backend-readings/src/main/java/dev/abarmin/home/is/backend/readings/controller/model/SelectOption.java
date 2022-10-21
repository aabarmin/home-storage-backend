package dev.abarmin.home.is.backend.readings.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
@AllArgsConstructor
public class SelectOption {
  private String key;
  private String value;

  public static SelectOption of(final Object key, final String value) {
    return new SelectOption(
        String.valueOf(key),
        value
    );
  }
}
