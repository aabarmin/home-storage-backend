package dev.abarmin.home.is.backend;

import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aleksandr Barmin
 */
@RestController
@RequestMapping("/health")
public class HealthController {
  @GetMapping
  public Map<String, String> health() {
    return Map.of(
        "health", "OK"
    );
  }

  @GetMapping("/env")
  public Map<String, String> env() {
    return Map.of(
        "name", "LOCAL",
        "version", "1.0.0-SNAPSHOT",
        "build", "today"
    );
  }
}
