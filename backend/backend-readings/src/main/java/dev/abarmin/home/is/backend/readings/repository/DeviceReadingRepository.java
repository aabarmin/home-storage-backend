package dev.abarmin.home.is.backend.readings.repository;

import dev.abarmin.home.is.backend.readings.domain.Device;
import dev.abarmin.home.is.backend.readings.domain.DeviceReading;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Aleksandr Barmin
 */
public interface DeviceReadingRepository extends CrudRepository<DeviceReading, Integer> {
  Optional<DeviceReading> findDeviceReadingByFlatAndDeviceAndDate(
      Flat flat,
      Device device,
      LocalDate date
  );

  Iterable<DeviceReading> findDeviceReadingByFlat(Flat flat);

  Iterable<DeviceReading> findDeviceReadingByYearAndFlat(int year, Flat flat);
}
