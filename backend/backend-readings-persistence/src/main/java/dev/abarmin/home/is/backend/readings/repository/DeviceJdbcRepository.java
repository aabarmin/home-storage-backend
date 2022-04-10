package dev.abarmin.home.is.backend.readings.repository;

import dev.abarmin.home.is.backend.readings.domain.DeviceEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Aleksandr Barmin
 */
public interface DeviceJdbcRepository extends CrudRepository<DeviceEntity, Integer> {
  Iterable<DeviceEntity> findDevicesByFlat(int flatId);

  Optional<DeviceEntity> findDeviceByAlias(String alias);
}
