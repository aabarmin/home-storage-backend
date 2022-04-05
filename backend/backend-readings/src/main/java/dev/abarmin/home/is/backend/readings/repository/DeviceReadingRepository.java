package dev.abarmin.home.is.backend.readings.repository;

import dev.abarmin.home.is.backend.readings.domain.DeviceReading;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Aleksandr Barmin
 */
public interface DeviceReadingRepository extends CrudRepository<DeviceReading, Integer> {
  Iterable<DeviceReading> findDeviceReadingByYear(int year);

  Iterable<DeviceReading> findDeviceReadingByFlat(Flat flat);

  Iterable<DeviceReading> findDeviceReadingByYearAndFlat(int year, Flat flat);
}
