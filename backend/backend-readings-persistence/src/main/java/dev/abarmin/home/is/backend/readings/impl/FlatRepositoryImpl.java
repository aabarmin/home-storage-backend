package dev.abarmin.home.is.backend.readings.impl;

import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.domain.FlatEntity;
import dev.abarmin.home.is.backend.readings.repository.FlatJdbcRepository;
import dev.abarmin.home.is.backend.readings.repository.FlatRepository;
import dev.abarmin.home.is.backend.readings.transformer.FlatEntityTransformer;
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
public class FlatRepositoryImpl implements FlatRepository {
  private final FlatEntityTransformer transformer;
  private final FlatJdbcRepository repository;

  @Override
  public Flat save(Flat flat) {
    final FlatEntity flatEntity = transformer.toEntity(flat);
    final FlatEntity savedEntity = repository.save(flatEntity);
    return transformer.toDomain(savedEntity);
  }

  @Override
  public Optional<Flat> findById(int id) {
    return repository.findById(id)
        .map(transformer::toDomain);
  }

  @Override
  public Collection<Flat> findAll() {
    return StreamSupport.stream(repository.findAll().spliterator(), false)
        .map(transformer::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Flat> findFirstByAlias(String alias) {
    return repository.findFlatEntityByAlias(alias)
        .map(transformer::toDomain);
  }

  @Override
  public void deleteById(int id) {
    repository.deleteById(id);
  }
}
