package dev.abarmin.home.is.backend.readings.model;

/**
 * Interface that shows that the element can accept visitors.
 *
 * @author Aleksandr Barmin
 */
public interface ConfigurationElement {
  void accept(ConfigurationVisitor visitor);
}
