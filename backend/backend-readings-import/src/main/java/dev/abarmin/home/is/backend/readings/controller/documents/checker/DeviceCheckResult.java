package dev.abarmin.home.is.backend.readings.controller.documents.checker;

/**
 * @author Aleksandr Barmin
 */
public record DeviceCheckResult(
    String name,
    String alias,
    boolean available
) {
}
