package dev.abarmin.home.is.backend.security;

import java.security.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aleksandr Barmin
 */
@RestController
public class TestSecurityController {
  @GetMapping("/")
  public String hello(Principal principal,
                      Authentication authentication,
                      @AuthenticationPrincipal Jwt jwtToken) {
    return "Hello, World!";
  }
}
