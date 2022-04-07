package dev.abarmin.home.is.backend.readings.model.device;

import dev.abarmin.home.is.backend.readings.model.ConfigurationElement;
import dev.abarmin.home.is.backend.readings.model.ConfigurationVisitor;
import java.util.Collection;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
public class ImportDevice implements ConfigurationElement {
  private String name;
  private String alias;
  private Collection<ImportDeviceFeature> features;

  @Override
  public void accept(ConfigurationVisitor visitor) {
    visitor.accept(this);
  }
}
