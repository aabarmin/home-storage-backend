package dev.abarmin.home.is.backend.readings.controller;

import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.rest.transformer.FlatTransformer;
import dev.abarmin.home.is.backend.readings.service.FlatService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Aleksandr Barmin
 */
@Controller
@RequestMapping("/readings/flats")
public class FlatsController {
  @Autowired
  private FlatService flatService;

  @Autowired
  private FlatTransformer flatTransformer;

  @ModelAttribute("flat")
  public Flat flatModel() {
    return new Flat();
  }

  @GetMapping
  public ModelAndView viewAll(final ModelAndView modelAndView) {
    modelAndView.setViewName("flats/index");
    modelAndView.addObject("flats", flatService.findAll());
    return modelAndView;
  }

  @GetMapping("/new")
  public String createNew() {
    return "flats/edit";
  }

  @PostMapping
  public String save(final @Valid @ModelAttribute("flat") Flat model,
                           final BindingResult result) {
    if (result.hasErrors()) {
      return "flats/edit";
    }
    flatService.save(model);
    return "redirect:/readings/flats";
  }
}
