package dev.abarmin.home.is.backend.mrp.domain;

/**
 * Available types of consumptions.
 *
 * @author Aleksandr Barmin
 */
public enum ConsumptionType {
  /**
   * The whole unit is consumed at a time.
   */
  UNIT_CONSUMPTION,

  /**
   * Part of a unit is consumed at a time.
   */
  PARTIAL_CONSUMPTION
}
