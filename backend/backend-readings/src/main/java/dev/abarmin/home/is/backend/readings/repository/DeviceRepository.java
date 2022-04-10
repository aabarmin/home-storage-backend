package dev.abarmin.home.is.backend.readings.repository;

import dev.abarmin.home.is.backend.readings.domain.Device;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import java.util.Collection;
import java.util.Optional;

/**
 * @author Aleksandr Barmin
 */
public interface DeviceRepository {
  Device save(Device device);

  Optional<Device> findById(int id);

  @Deprecated
  Collection<Device> findAll();

  Iterable<Device> findDevicesByFlat(Flat flat);

  Optional<Device> findDeviceByAlias(String alias);
}
