package dev.abarmin.home.is.backend.readings.service;

import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.repository.FlatRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Aleksandr Barmin
 */
@Service
@RequiredArgsConstructor
public class FlatService {
  private final FlatRepository flatRepository;

  public Iterable<Flat> findAll() {
    return flatRepository.findAll();
  }

  public Optional<Flat> findOneByAlias(final String alias) {
    return flatRepository.findFirstByAlias(alias);
  }

  public Optional<Flat> findOneById(final int id) {
    return flatRepository.findById(id);
  }

  public Flat save(final Flat flat) {
    return flatRepository.save(flat);
  }
}
