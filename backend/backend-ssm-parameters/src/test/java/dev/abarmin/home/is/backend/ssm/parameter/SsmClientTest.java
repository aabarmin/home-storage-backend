package dev.abarmin.home.is.backend.ssm.parameter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aleksandr Barmin
 */
class SsmClientTest {
  SsmClient client = new SsmClient();

  @ParameterizedTest
  @CsvSource({
      "aws-ssm:/ab-test,true",
      "aws.ssm.ab.test,false"
  })
  void check_supportedPrefixes(final String parameter, final boolean supported) {
    final boolean clientSupports = client.isSupported(parameter);
    assertThat(clientSupports).isEqualTo(supported);
  }
}