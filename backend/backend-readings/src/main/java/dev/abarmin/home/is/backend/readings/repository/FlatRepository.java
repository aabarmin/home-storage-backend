package dev.abarmin.home.is.backend.readings.repository;

import dev.abarmin.home.is.backend.readings.domain.Flat;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Aleksandr Barmin
 */
public interface FlatRepository extends CrudRepository<Flat, Integer> {
  Optional<Flat> findFirstByAlias(String alias);
}
