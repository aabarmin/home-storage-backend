package dev.abarmin.home.is.backend.readings.rest;

import dev.abarmin.home.is.backend.readings.domain.DeviceReading;
import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.repository.DeviceReadingRepository;
import dev.abarmin.home.is.backend.readings.rest.model.DeviceReadingModel;
import dev.abarmin.home.is.backend.readings.rest.transformer.DeviceReadingTransformer;
import dev.abarmin.home.is.backend.readings.service.DeviceReadingService;
import dev.abarmin.home.is.backend.readings.service.DeviceService;
import dev.abarmin.home.is.backend.readings.service.FlatService;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aleksandr Barmin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/records")
public class DeviceReadingController {
  private final DeviceReadingRepository repository;
  private final DeviceReadingService service;
  private final FlatService flatService;
  private final DeviceReadingTransformer transformer;

  @PostMapping
  public DeviceReadingModel save(final @RequestBody DeviceReadingModel model) {

    final DeviceReading domain = transformer.toDomain(model);
    final DeviceReading saved = service.save(domain);
    return transformer.toModel(saved);
  }

  @PutMapping("/{id}")
  public DeviceReadingModel update(final @PathVariable("id") int id,
                                   final @RequestBody DeviceReadingModel model) {

    final DeviceReading updated = transformer.toDomain(model);
    final DeviceReading saved = service.save(updated);
    return transformer.toModel(saved);
  }

  @GetMapping(params = "flatId")
  public Collection<DeviceReadingModel> findByFlat(final @RequestParam("flatId") int flatId) {
    final Flat flat = flatService.findOneById(flatId)
        .orElseThrow();

    return StreamSupport.stream(repository.findDeviceReadingByFlat(flat).spliterator(), false)
        .map(transformer::toModel)
        .collect(Collectors.toList());
  }

  @GetMapping(params = {"flatId", "year"})
  public Collection<DeviceReadingModel> findByFlatAndYear(final @RequestParam("flatId") int flatId,
                                                          final @RequestParam("year") int year) {

    final Flat flat = flatService.findOneById(flatId)
        .orElseThrow();

    return StreamSupport.stream(
            repository.findDeviceReadingByYearAndFlat(year, flat).spliterator(),
            false
        )
        .map(transformer::toModel)
        .collect(Collectors.toList());
  }
}
