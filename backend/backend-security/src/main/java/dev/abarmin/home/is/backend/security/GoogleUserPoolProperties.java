package dev.abarmin.home.is.backend.security;

import java.util.Collection;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * @author Aleksandr Barmin
 */
@Data
@Validated
@ConfigurationProperties(prefix = "security.google")
public class GoogleUserPoolProperties {
  @NotEmpty
  private String clientId;

  @NotEmpty
  private String clientSecret;

  @NotEmpty
  private Collection<String> allowedUsers;
}
