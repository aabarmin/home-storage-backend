package dev.abarmin.home.is.backend.ssm.parameter;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Client for AWS System Manager Parameter Store.
 *
 * @author Aleksandr Barmin
 */
@Slf4j
public class SsmClient {
  public static final String SSM_PARAMETER_PREFIX = "aws-ssm:";

  private AWSSimpleSystemsManagement awsSsmClient = null;
  private final Map<String, String> parameters = new ConcurrentHashMap<>();

  public boolean isSupported(final String name) {
    return StringUtils.startsWithIgnoreCase(name, SSM_PARAMETER_PREFIX);
  }

  public String getParameterValue(final String name) {
    final String parameterName = getParameterName(name);
    return parameters.computeIfAbsent(parameterName, (param) -> {
      log.info("Getting value for parameter with name {}", param);
      final AWSSimpleSystemsManagement client = getSsmClient();
      final GetParameterResult result = client.getParameter(new GetParameterRequest().withName(param));
      return result.getParameter().getValue();
    });
  }

  private String getParameterName(final String name) {
    return StringUtils.substringAfter(name, SSM_PARAMETER_PREFIX);
  }

  private AWSSimpleSystemsManagement getSsmClient() {
    if (this.awsSsmClient == null) {
      this.awsSsmClient = AWSSimpleSystemsManagementClientBuilder
          .standard()
          .build();
    }
    return this.awsSsmClient;
  }

}
