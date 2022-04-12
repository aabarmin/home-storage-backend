package dev.abarmin.home.is.backend.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Aleksandr Barmin
 */
@Controller
public class NoAccessController {
  @GetMapping("/no-access")
  public ModelAndView noAccess(final ModelAndView modelAndView) {
    modelAndView.setViewName("no-access");
    return modelAndView;
  }
}
