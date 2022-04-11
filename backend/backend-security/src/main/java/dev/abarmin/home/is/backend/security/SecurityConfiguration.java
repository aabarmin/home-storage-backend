package dev.abarmin.home.is.backend.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Aleksandr Barmin
 */
@Configuration
@EnableConfigurationProperties(CognitoUserPoolProperties.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests(auth -> auth.anyRequest().authenticated())
        .oauth2Login();
  }
}
