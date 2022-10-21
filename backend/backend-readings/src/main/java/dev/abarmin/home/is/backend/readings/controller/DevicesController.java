package dev.abarmin.home.is.backend.readings.controller;

import dev.abarmin.home.is.backend.readings.controller.model.SelectOption;
import dev.abarmin.home.is.backend.readings.domain.Device;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.repository.DeviceRepository;
import dev.abarmin.home.is.backend.readings.repository.FlatRepository;
import java.util.stream.Collectors;
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
@RequestMapping("/readings/flats/{flatId}/devices")
public class DevicesController {
  @Autowired
  private FlatRepository flatRepository;

  @Autowired
  private DeviceRepository deviceRepository;

  @ModelAttribute("device")
  public Device device(final @PathVariable("flatId") int flatId) {
    return Device.builder()
        .flatId(flatId)
        .build();
  }

  @ModelAttribute("flats")
  public Iterable<SelectOption> flats() {
    return flatRepository.findAll()
        .stream()
        .map(flat -> SelectOption.of(
            flat.getId(),
            flat.getTitle()
        ))
        .collect(Collectors.toList());
  }

  @GetMapping("/new")
  public String createNew() {
    return "devices/edit";
  }

  @GetMapping("/{deviceId}")
  public String edit(final @PathVariable("deviceId") int deviceId,
                     final Model model) {
    var device = deviceRepository.findById(deviceId)
        .orElseThrow(() -> new RuntimeException(String.format(
            "No device with id %s",
            deviceId
        )));

    model.addAttribute("device", device);
    return "devices/edit";
  }

  @GetMapping("/{deviceId}/delete")
  public String delete(final @PathVariable("deviceId") int deviceId,
                       final @PathVariable("flatId") int flatId) {
    deviceRepository.deleteById(deviceId);
    return "redirect:/readings/flats/" + flatId;
  }

  @PostMapping
  public String save(final @Valid @ModelAttribute("device") Device device,
                     final BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "devices/edit";
    }
    deviceRepository.save(device);
    return "redirect:/readings/flats/" + device.getFlatId();
  }
}
