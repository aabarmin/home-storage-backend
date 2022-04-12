package dev.abarmin.home.is.backend.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Aleksandr Barmin
 */
public class AuthorityHelper {
  public static boolean hasAuthority(final Authentication authentication,
                                     final String authority) {
    for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
      if (StringUtils.equalsIgnoreCase(grantedAuthority.getAuthority(), authority)) {
        return true;
      }
    }
    return false;
  }
}
