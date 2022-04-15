package dev.abarmin.home.is.backend.config;

import dev.abarmin.home.is.backend.converter.BooleanToIntegerConverter;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

/**
 * @author Aleksandr Barmin
 */
@Configuration
public class JdbcConverterConfiguration extends AbstractJdbcConfiguration {
  @Override
  protected List<?> userConverters() {
    return List.of(
        new BooleanToIntegerConverter()
    );
  }
}
