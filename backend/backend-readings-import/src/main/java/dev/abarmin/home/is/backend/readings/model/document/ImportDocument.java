package dev.abarmin.home.is.backend.readings.model.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.abarmin.home.is.backend.readings.model.ConfigurationElement;
import dev.abarmin.home.is.backend.readings.model.ConfigurationVisitor;
import dev.abarmin.home.is.backend.readings.model.device.ImportDeviceFeature;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
public class ImportDocument implements ConfigurationElement {
  @JsonProperty("device")
  private String deviceAlias;
  @JsonProperty("path")
  private String path;
  private ImportDeviceFeature type;
  @JsonProperty("name-pattern")
  private String namePattern;

  public void accept(ConfigurationVisitor visitor) {
    visitor.accept(this);
  }
}
