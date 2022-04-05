package dev.abarmin.home.is.backend.readings.repository;

import dev.abarmin.home.is.backend.readings.domain.Device;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Aleksandr Barmin
 */
public interface DeviceRepository extends CrudRepository<Device, Integer> {
  Iterable<Device> findDevicesByFlat(Integer flatId);

  Optional<Device> findDeviceByAlias(String alias);
}
