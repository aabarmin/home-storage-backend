package dev.abarmin.home.is.backend.mrp.validator;

/**
 * @author Aleksandr Barmin
 */
public class ValidationException extends RuntimeException {
  public ValidationException(final String message) {
    super(message);
  }
}
