package dev.abarmin.home.is.backend.readings.controller.documents.checker;

/**
 * @author Aleksandr Barmin
 */
public record ValidationMessage(
    String message,
    ValidationMessageSeverity severity
) {

  public static ValidationMessage warning(final String message) {
    return new ValidationMessage(message, ValidationMessageSeverity.WARNING);
  }

  public static ValidationMessage info(final String message) {
    return new ValidationMessage(message, ValidationMessageSeverity.INFO);
  }

  public boolean isInfo() {
    return ValidationMessageSeverity.INFO == severity;
  }

  public boolean isError() {
    return ValidationMessageSeverity.ERROR == severity;
  }
}
