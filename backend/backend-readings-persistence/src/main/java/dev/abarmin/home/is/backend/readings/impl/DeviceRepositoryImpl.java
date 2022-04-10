package dev.abarmin.home.is.backend.readings.impl;

import dev.abarmin.home.is.backend.readings.domain.Device;
import dev.abarmin.home.is.backend.readings.domain.DeviceEntity;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.repository.DeviceJdbcRepository;
import dev.abarmin.home.is.backend.readings.repository.DeviceRepository;
import dev.abarmin.home.is.backend.readings.transformer.DeviceEntityTransformer;
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
public class DeviceRepositoryImpl implements DeviceRepository {
  private final DeviceJdbcRepository repository;
  private final DeviceEntityTransformer transformer;

  @Override
  public Device save(Device device) {
    final DeviceEntity deviceEntity = transformer.toEntity(device);
    final DeviceEntity savedEntity = repository.save(deviceEntity);
    return transformer.toDomain(savedEntity);
  }

  @Override
  public Optional<Device> findById(int id) {
    return repository.findById(id)
        .map(transformer::toDomain);
  }

  @Override
  public Collection<Device> findAll() {
    return StreamSupport.stream(
        repository.findAll().spliterator(),
        false
    )
        .map(transformer::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Iterable<Device> findDevicesByFlat(Flat flat) {
    final Iterable<DeviceEntity> iterable = repository.findDevicesByFlat(flat.id());

    return StreamSupport.stream(iterable.spliterator(), false)
        .map(transformer::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Device> findDeviceByAlias(String alias) {
    return repository.findDeviceByAlias(alias)
        .map(transformer::toDomain);
  }
}
