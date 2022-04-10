package dev.abarmin.home.is.backend.readings.impl;

import dev.abarmin.home.is.backend.readings.domain.Device;
import dev.abarmin.home.is.backend.readings.domain.DeviceReading;
import dev.abarmin.home.is.backend.readings.domain.DeviceReadingEntity;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.repository.DeviceReadingJdbcRepository;
import dev.abarmin.home.is.backend.readings.repository.DeviceReadingRepository;
import dev.abarmin.home.is.backend.readings.transformer.DeviceReadingEntityTransformer;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Aleksandr Barmin
 */
@Component
@RequiredArgsConstructor
public class DeviceReadingRepositoryImpl implements DeviceReadingRepository {
  private final DeviceReadingJdbcRepository repository;
  private final DeviceReadingEntityTransformer transformer;

  @Override
  public DeviceReading save(DeviceReading reading) {
    final DeviceReadingEntity readingEntity = transformer.toEntity(reading);
    final DeviceReadingEntity savedEntity = repository.save(readingEntity);
    return transformer.toDomain(savedEntity);
  }

  @Override
  public Optional<DeviceReading> findById(int readingId) {
    return repository.findById(readingId)
        .map(transformer::toDomain);
  }

  @Override
  public Optional<DeviceReading> findDeviceReadingByFlatAndDeviceAndDate(Flat flat,
                                                                         Device device,
                                                                         LocalDate date) {

    return repository.findDeviceReadingByFlatAndDeviceAndDate(
        flat.id(),
        device.id(),
        date
        )
            .map(transformer::toDomain);
  }

  @Override
  public Collection<DeviceReading> findDeviceReadingByFlat(Flat flat) {
    return StreamSupport.stream(
            repository.findDeviceReadingByFlat(flat.id()).spliterator(),
            false
        )
        .map(transformer::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Collection<DeviceReading> findDeviceReadingByYearAndFlat(int year, Flat flat) {
    final Iterable<DeviceReadingEntity> iterable = repository.findDeviceReadingByYearAndFlat(
        year,
        flat.id()
    );

    return StreamSupport.stream(iterable.spliterator(), false)
        .map(transformer::toDomain)
        .collect(Collectors.toList());
  }
}
