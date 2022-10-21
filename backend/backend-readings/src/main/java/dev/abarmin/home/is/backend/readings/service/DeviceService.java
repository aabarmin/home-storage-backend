package dev.abarmin.home.is.backend.readings.service;

import dev.abarmin.home.is.backend.readings.domain.Device;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.repository.DeviceRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Aleksandr Barmin
 */
@Service
@Deprecated
@RequiredArgsConstructor
public class DeviceService {
  private final DeviceRepository deviceRepository;

  @Deprecated
  public Iterable<Device> findAll() {
    return deviceRepository.findAll();
  }

  public Iterable<Device> findAllByFlat(final Flat flat) {
    return deviceRepository.findDevicesByFlat(flat);
  }

  public Optional<Device> findByAlias(final String alias) {
    return deviceRepository.findDeviceByAlias(alias);
  }

  public Optional<Device> findById(final int id) {
    return deviceRepository.findById(id);
  }

  public Device save(final Device device) {
    return deviceRepository.save(device);
  }
}
