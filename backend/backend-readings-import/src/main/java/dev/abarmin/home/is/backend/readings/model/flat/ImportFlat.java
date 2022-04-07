package dev.abarmin.home.is.backend.readings.model.flat;

import dev.abarmin.home.is.backend.readings.model.ConfigurationElement;
import dev.abarmin.home.is.backend.readings.model.ConfigurationVisitor;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
public class ImportFlat implements ConfigurationElement {
  private String name;
  private String alias;

  @Override
  public void accept(ConfigurationVisitor visitor) {
    visitor.accept(this);
  }
}
