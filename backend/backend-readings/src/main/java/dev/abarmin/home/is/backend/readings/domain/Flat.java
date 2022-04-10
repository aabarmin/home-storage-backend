package dev.abarmin.home.is.backend.readings.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Aleksandr Barmin
 */
@Table("FLATS")
public record Flat(
    @Id @Column("FLAT_ID") Integer id,
    @Column("FLAT_TITLE") String title,
    @Column("FLAT_ALIAS") String alias
) {
  @PersistenceConstructor
  public Flat {}

  public Flat(String title,
              String alias) {

    this(null, title, alias);
  }
}
