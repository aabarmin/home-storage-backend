package dev.abarmin.home.is.backend.readings.model;

import java.util.Collection;
import lombok.Data;

/**
 * Configuration for import. Just description regarding what should be where, not import itself.
 *
 * @author Aleksandr Barmin
 */
@Data
public class ImportConfiguration implements ConfigurationElement {
  /**
   * One source - one flat.
   */
  private Collection<ImportSource> sources;

  @Override
  public void accept(ConfigurationVisitor visitor) {
    visitor.accept(this);
    sources.forEach(source -> source.accept(visitor));
  }
}
