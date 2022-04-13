package dev.abarmin.home.is.backend.ssm.parameter;

import org.springframework.core.env.PropertySource;

/**
 * The property source that uses AWS SSM Parameter Store to resolve parameters.
 *
 * @author Aleksandr Barmin
 */
public class SsmParameterStorePropertySource extends PropertySource<SsmClient> {
  public SsmParameterStorePropertySource(String name, SsmClient source) {
    super(name, source);
  }

  @Override
  public Object getProperty(String name) {
    if (this.source.isSupported(name)) {
      return source.getParameterValue(name);
    }
    return null;
  }
}
