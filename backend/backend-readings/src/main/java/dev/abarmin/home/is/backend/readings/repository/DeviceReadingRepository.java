package dev.abarmin.home.is.backend.readings.repository;

import dev.abarmin.home.is.backend.readings.domain.Device;
import dev.abarmin.home.is.backend.readings.domain.DeviceReading;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

/**
 * @author Aleksandr Barmin
 */
public interface DeviceReadingRepository {
  DeviceReading save(DeviceReading reading);

  Optional<DeviceReading> findById(int readingId);

  Optional<DeviceReading> findDeviceReadingByFlatAndDeviceAndDate(
      Flat flat,
      Device device,
      LocalDate date
  );

  Collection<DeviceReading> findDeviceReadingByFlat(Flat flat);

  Collection<DeviceReading> findDeviceReadingByYearAndFlat(int year, Flat flat);
}
