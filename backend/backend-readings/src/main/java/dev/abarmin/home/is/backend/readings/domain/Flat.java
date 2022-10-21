package dev.abarmin.home.is.backend.readings.domain;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Aleksandr Barmin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flat {
  private Integer id;

  @NotEmpty
  private String title;

  @NotEmpty
  private String alias;
}
