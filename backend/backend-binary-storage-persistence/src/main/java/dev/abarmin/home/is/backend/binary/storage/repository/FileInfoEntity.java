package dev.abarmin.home.is.backend.binary.storage.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Aleksandr Barmin
 */
@Table("files")
public record FileInfoEntity(
    @Id @Column("file_id") Integer id,
    @Column("file_name") String fileName,
    @Column("file_type") String fileType,
    @Column("file_path") String filePath) {
}
