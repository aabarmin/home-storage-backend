package dev.abarmin.home.is.backend.security;

import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author Aleksandr Barmin
 */
@Data
@Validated
@ConfigurationProperties(prefix = "security.cognito")
public class CognitoUserPoolProperties {
  @NotEmpty
  private String region;

  @NotEmpty
  private String userPoolId;

  @NotEmpty
  private String clientId;

  @NotEmpty
  private String clientSecret;
}
