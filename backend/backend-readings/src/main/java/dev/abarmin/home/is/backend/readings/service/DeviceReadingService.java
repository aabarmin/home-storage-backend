package dev.abarmin.home.is.backend.readings.service;

import dev.abarmin.home.is.backend.readings.domain.Device;
import dev.abarmin.home.is.backend.readings.domain.DeviceReading;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.repository.DeviceReadingRepository;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Aleksandr Barmin
 */
@Service
@RequiredArgsConstructor
public class DeviceReadingService {
  private final DeviceReadingRepository repository;

  public Optional<DeviceReading> findDeviceReadingByFlatAndDeviceAndDate(
      Flat flat,
      Device device,
      LocalDate date
  ) {
    return repository.findDeviceReadingByFlatAndDeviceAndDate(flat, device, date);
  }

  public Optional<DeviceReading> findById(final int id) {
    return repository.findById(id);
  }

  public DeviceReading save(final DeviceReading domain) {
    return repository.save(domain);
  }
}
