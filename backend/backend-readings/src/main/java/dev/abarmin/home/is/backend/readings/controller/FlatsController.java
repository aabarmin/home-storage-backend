package dev.abarmin.home.is.backend.readings.controller;

import dev.abarmin.home.is.backend.readings.domain.Device;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.repository.DeviceRepository;
import dev.abarmin.home.is.backend.readings.repository.FlatRepository;
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

/**
 * @author Aleksandr Barmin
 */
@Controller
@RequestMapping("/readings/flats")
public class FlatsController {
  @Autowired
  private FlatRepository flatRepository;

  @Autowired
  private DeviceRepository deviceRepository;

  @ModelAttribute("flat")
  public Flat flatModel() {
    return new Flat();
  }

  @GetMapping
  public String viewAll(final Model model) {
    model.addAttribute("flats", flatRepository.findAll());
    return "flats/index";
  }

  @GetMapping("/new")
  public String createNew() {
    return "flats/edit";
  }

  @GetMapping("/{id}")
  public String edit(final @PathVariable("id") int id,
                     final Model model) {

    var flat = flatRepository.findById(id).orElseThrow();
    var devices = deviceRepository.findDevicesByFlat(flat);

    model.addAttribute("flat", flat);
    model.addAttribute("devices", devices);
    return "flats/edit";
  }

  @GetMapping("/{id}/delete")
  public String delete(final @PathVariable("id") int id) {
    flatRepository.deleteById(id);
    return "redirect:/readings/flats";
  }

  @PostMapping
  public String save(final @Valid @ModelAttribute("flat") Flat model,
                     final BindingResult result) {
    if (result.hasErrors()) {
      return "flats/edit";
    }
    flatRepository.save(model);
    return "redirect:/readings/flats";
  }
}
