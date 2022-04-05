package dev.abarmin.home.is.backend.readings.service;

import dev.abarmin.home.is.backend.readings.domain.FileInfo;
import dev.abarmin.home.is.backend.readings.repository.FileInfoRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Aleksandr Barmin
 */
@Service
@RequiredArgsConstructor
public class FileInfoService {
  private final FileInfoRepository repository;

  public Optional<FileInfo> findById(final int id) {
    return repository.findById(id);
  }

  public FileInfo save(final FileInfo domain) {
    return repository.save(domain);
  }
}
