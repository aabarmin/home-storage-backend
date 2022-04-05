package dev.abarmin.home.is.backend.readings.rest;

import dev.abarmin.home.is.backend.readings.domain.Flat;
import dev.abarmin.home.is.backend.readings.rest.model.FlatModel;
import dev.abarmin.home.is.backend.readings.rest.transformer.FlatTransformer;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aleksandr Barmin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/flats")
public class FlatController {
  private final FlatService flatService;
  private final FlatTransformer flatTransformer;

  @GetMapping
  public Collection<FlatModel> findAll() {
    return StreamSupport.stream(flatService.findAll().spliterator(), false)
        .map(flatTransformer::toModel)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public FlatModel findOne(final @PathVariable("id") int id) {
    return flatService.findOneById(id)
        .map(flatTransformer::toModel)
        .orElseThrow();
  }

  @PostMapping
  public FlatModel save(final @RequestBody FlatModel model) {
    final Optional<Flat> byAlias = flatService.findOneByAlias(model.alias());
    if (byAlias.isPresent()) {
      throw new RuntimeException(String.format(
          "Flat with alias %s already exists",
          model.alias()
      ));
    }

    final Flat domain = flatTransformer.toDomain(model);
    final Flat saved = flatService.save(domain);
    return flatTransformer.toModel(saved);
  }
}
