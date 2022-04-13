package dev.abarmin.home.is.backend.ssm.parameter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * This class registers a new parameter store in the application context.
 *
 * @author Aleksandr Barmin
 */
@Slf4j
public class SsmParameterStoreApplicationContextInitializer implements ApplicationContextInitializer {
  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    applicationContext.getEnvironment()
        .getPropertySources()
        .addFirst(new SsmParameterStorePropertySource("AWS SSM Parameter Store", new SsmClient()));
  }
}
