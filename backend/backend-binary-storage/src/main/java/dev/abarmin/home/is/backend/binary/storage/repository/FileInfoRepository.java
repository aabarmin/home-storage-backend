package dev.abarmin.home.is.backend.binary.storage.repository;

import dev.abarmin.home.is.backend.binary.storage.domain.FileInfo;
import java.util.Optional;

/**
 * @author Aleksandr Barmin
 */
public interface FileInfoRepository {
  /**
   * Find file info record by id.
   * @param id
   * @return
   */
  Optional<FileInfo> findById(int id);

  /**
   * Save a file info record.
   * @param fileInfo
   * @return
   */
  FileInfo save(FileInfo fileInfo);
}
