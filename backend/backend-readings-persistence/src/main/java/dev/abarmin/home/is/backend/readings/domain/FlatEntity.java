package dev.abarmin.home.is.backend.readings.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Aleksandr Barmin
 */
@Table("FLATS")
public record FlatEntity(
    @Id @Column("FLAT_ID") Integer id,
    @Column("FLAT_TITLE") String title,
    @Column("FLAT_ALIAS") String alias
) {

}
