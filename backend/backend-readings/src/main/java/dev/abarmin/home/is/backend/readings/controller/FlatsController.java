package dev.abarmin.home.is.backend.readings.controller;

import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.rest.transformer.FlatTransformer;
import dev.abarmin.home.is.backend.readings.service.FlatService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
  public String viewAll(final Model model) {
    model.addAttribute("flats", flatService.findAll());
    return "flats/index";
  }

  @GetMapping("/new")
  public String createNew() {
    return "flats/edit";
  }

  @GetMapping("/{id}")
  public String edit(final @PathVariable("id") int id,
                     final Model model) {

    model.addAttribute("flat", flatService.findOneById(id).orElseThrow());
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
