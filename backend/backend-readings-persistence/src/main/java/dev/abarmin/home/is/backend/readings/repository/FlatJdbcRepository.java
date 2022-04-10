package dev.abarmin.home.is.backend.readings.repository;

import dev.abarmin.home.is.backend.readings.domain.FlatEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Aleksandr Barmin
 */
public interface FlatJdbcRepository extends CrudRepository<FlatEntity, Integer> {
  Optional<FlatEntity> findFlatEntityByAlias(String alias);
}
