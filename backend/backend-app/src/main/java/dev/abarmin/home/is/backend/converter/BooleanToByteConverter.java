package dev.abarmin.home.is.backend.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * @author Aleksandr Barmin
 */
@ReadingConverter
public class BooleanToByteConverter implements Converter<Byte, Boolean> {
  @Override
  public Boolean convert(Byte source) {
    return source.intValue() == 1;
  }
}
