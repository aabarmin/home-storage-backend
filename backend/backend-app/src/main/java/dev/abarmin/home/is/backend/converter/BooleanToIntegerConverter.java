package dev.abarmin.home.is.backend.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * @author Aleksandr Barmin
 */
@ReadingConverter
public class BooleanToIntegerConverter implements Converter<Integer, Boolean> {
  @Override
  public Boolean convert(Integer source) {
    return source.intValue() == 1;
  }
}
