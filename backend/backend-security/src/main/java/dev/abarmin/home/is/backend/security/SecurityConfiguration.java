package dev.abarmin.home.is.backend.security;

import com.nimbusds.jose.shaded.json.JSONArray;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * @author Aleksandr Barmin
 */
@Configuration
@EnableConfigurationProperties(CognitoUserPoolProperties.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Autowired
  private CognitoUserPoolProperties properties;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests(auth ->
            auth.antMatchers("/no-access").authenticated()
                .antMatchers("/logout").authenticated()
                .anyRequest().hasAuthority("ROLE_HOME_USER")
        )
        .oauth2Login().successHandler(authenticationSuccessHandler())
        .and().csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
  }

  private AuthenticationSuccessHandler authenticationSuccessHandler() {
    return new AuthenticationSuccessHandler() {
      private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

      @Override
      public void onAuthenticationSuccess(HttpServletRequest request,
                                          HttpServletResponse response,
                                          Authentication authentication) throws IOException, ServletException {
        /**
         * If user has an expected authority, no special redirect. If the user has no
         * needed permissions, the user should be redirected to the no-access page.
         */
        if (AuthorityHelper.hasAuthority(authentication, "ROLE_HOME_USER")) {
          redirectStrategy.sendRedirect(request, response, "/");
          return;
        }
        redirectStrategy.sendRedirect(request, response, "/no-access");
      }
    };
  }

  @Bean
  public GrantedAuthoritiesMapper authoritiesMapper() {
    return authorities -> {
      final Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
      for (GrantedAuthority authority : authorities) {
        if (OidcUserAuthority.class.isAssignableFrom(authority.getClass())) {
          final OidcUserAuthority userAuthority = OidcUserAuthority.class.cast(authority);
          if (userAuthority.getAttributes().containsKey("cognito:groups")) {
            final Object groupsObject = userAuthority.getAttributes().get("cognito:groups");
            final JSONArray groupsArray = JSONArray.class.cast(groupsObject);
            if (groupsArray.contains(properties.getTargetUserGroup())) {
              grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_HOME_USER"));
            }
          }
        }
      }
      return grantedAuthorities;
    };
  }
}
