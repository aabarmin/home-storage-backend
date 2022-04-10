package dev.abarmin.home.is.backend.readings.repository;

import dev.abarmin.home.is.backend.readings.domain.DeviceReadingEntity;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Aleksandr Barmin
 */
public interface DeviceReadingJdbcRepository extends CrudRepository<DeviceReadingEntity, Integer> {
  Iterable<DeviceReadingEntity> findDeviceReadingByYearAndFlat(int year, int flat);

  Iterable<DeviceReadingEntity> findDeviceReadingByFlat(int flat);

  Optional<DeviceReadingEntity> findDeviceReadingByFlatAndDeviceAndDate(int flat,
                                                                  int device,
                                                                  LocalDate date);
}
