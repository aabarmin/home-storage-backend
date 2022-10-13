package dev.abarmin.home.is.backend.readings.repository;

import dev.abarmin.home.is.backend.readings.domain.Flat;
import java.util.Collection;
import java.util.Optional;

/**
 * @author Aleksandr Barmin
 */
public interface FlatRepository {
  Flat save(Flat flat);

  Optional<Flat> findById(int id);

  @Deprecated
  Collection<Flat> findAll();

  Optional<Flat> findFirstByAlias(String alias);

  void deleteById(int id);
}
