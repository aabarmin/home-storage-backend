package dev.abarmin.home.is.backend.readings.rest;

import dev.abarmin.home.is.backend.readings.domain.Device;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.rest.model.DeviceModel;
import dev.abarmin.home.is.backend.readings.rest.transformer.DeviceModelTransformer;
import dev.abarmin.home.is.backend.readings.service.DeviceService;
import dev.abarmin.home.is.backend.readings.service.FlatService;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aleksandr Barmin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/devices")
public class DeviceController {
  private final DeviceService deviceService;
  private final FlatService flatService;
  private final DeviceModelTransformer deviceTransformer;

  @GetMapping
  public Collection<DeviceModel> findAll() {
    return StreamSupport.stream(deviceService.findAll().spliterator(), false)
        .map(deviceTransformer::toModel)
        .collect(Collectors.toList());
  }

  @GetMapping(params = "flatId")
  public Collection<DeviceModel> findAll(final @RequestParam("flatId") int flatId) {
    final Optional<Flat> byId = flatService.findOneById(flatId);
    final Flat flat = byId.orElseThrow();

    return StreamSupport.stream(deviceService.findAllByFlat(flat).spliterator(), false)
        .map(deviceTransformer::toModel)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public DeviceModel findOne(final @PathVariable("id") int deviceId) {
    final Device device = deviceService.findById(deviceId).orElseThrow();
    return deviceTransformer.toModel(device);
  }

  @PostMapping
  public DeviceModel save(final @RequestBody DeviceModel model) {
    final Optional<Flat> flatById = flatService.findOneById(model.flatId());
    if (flatById.isEmpty()) {
      throw new RuntimeException(String.format(
          "No flat with id %s",
          model.flatId()
      ));
    }

    final Optional<Device> byAlias = deviceService.findByAlias(model.alias());
    if (byAlias.isPresent()) {
      throw new RuntimeException(String.format(
          "Device with alias %s already exists",
          model.alias()
      ));
    }

    final Device deviceDomain = deviceTransformer.toDomain(model);
    final Device saved = deviceService.save(deviceDomain);
    return deviceTransformer.toModel(saved);
  }
}
