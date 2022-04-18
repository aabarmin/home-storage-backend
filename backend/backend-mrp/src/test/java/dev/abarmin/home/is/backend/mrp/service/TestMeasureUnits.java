package dev.abarmin.home.is.backend.mrp.service;

import dev.abarmin.home.is.backend.mrp.domain.MeasureUnitDTO;

/**
 * @author Aleksandr Barmin
 */
public class TestMeasureUnits {
  private static final MeasureUnitDTO KG = MeasureUnitDTO.builder()
      .name("kg")
      .alias("kg")
      .build();

  public static MeasureUnitDTO kg() {
    return KG;
  }
}
