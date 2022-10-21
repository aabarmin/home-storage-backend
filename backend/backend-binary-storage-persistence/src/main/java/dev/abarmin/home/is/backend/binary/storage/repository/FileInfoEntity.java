package dev.abarmin.home.is.backend.binary.storage.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Aleksandr Barmin
 */
@Table("FILES")
public record FileInfoEntity(
    @Id @Column("FILE_ID") Integer id,
    @Column("FILE_NAME") String fileName,
    @Column("FILE_TYPE") String fileType,
    @Column("FILE_PATH") String filePath) {
}
