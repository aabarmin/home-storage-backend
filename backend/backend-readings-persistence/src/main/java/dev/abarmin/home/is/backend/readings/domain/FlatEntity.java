package dev.abarmin.home.is.backend.readings.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Aleksandr Barmin
 */
@Table("flats")
public record FlatEntity(
    @Id @Column("flat_id") Integer id,
    @Column("flat_title") String title,
    @Column("flat_alias") String alias
) {

}
