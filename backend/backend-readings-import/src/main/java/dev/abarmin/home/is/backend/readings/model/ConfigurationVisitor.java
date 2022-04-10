package dev.abarmin.home.is.backend.readings.model;

import dev.abarmin.home.is.backend.readings.model.device.ImportDevice;
import dev.abarmin.home.is.backend.readings.model.document.ImportDocument;
import dev.abarmin.home.is.backend.readings.model.flat.ImportFlat;

/**
 * Visitor interface.
 *
 * @author Aleksandr Barmin
 */
public interface ConfigurationVisitor {
  /**
   * Visit the configuration.
   *
   * @param configuration
   */
  default void accept(ImportConfiguration configuration) {

  }

  /**
   * Visit the import source.
   * @param source
   */
  default void accept(ImportSource source) {

  }

  default void accept(ImportDevice importDevice) {

  }

  default void accept(ImportDocument importDocument) {

  }

  default void accept(ImportFlat importFlat) {

  }
}
