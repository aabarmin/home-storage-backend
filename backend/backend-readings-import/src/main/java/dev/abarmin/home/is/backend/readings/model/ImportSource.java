package dev.abarmin.home.is.backend.readings.model;

import dev.abarmin.home.is.backend.readings.model.device.ImportDevice;
import dev.abarmin.home.is.backend.readings.model.document.ImportDocument;
import dev.abarmin.home.is.backend.readings.model.flat.ImportFlat;
import java.util.Collection;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
public class ImportSource implements ConfigurationElement {
  private ImportFlat flat;
  private Collection<ImportDevice> devices;
  private Collection<ImportDocument> documents;

  @Override
  public void accept(ConfigurationVisitor visitor) {
    visitor.accept(this);
    devices.forEach(device -> device.accept(visitor));
    documents.forEach(doc -> doc.accept(visitor));
  }
}
