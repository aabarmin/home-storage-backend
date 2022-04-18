package dev.abarmin.home.is.backend.mrp.service;

import com.google.common.collect.Iterables;
import dev.abarmin.home.is.backend.mrp.domain.ResourceDTO;
import dev.abarmin.home.is.backend.mrp.service.validator.NewResourceLeftoverValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.google.common.base.Preconditions.*;

/**
 * @author Aleksandr Barmin
 */
@Service
@Validated
public class ResourceServiceImpl implements ResourceService {
  @Autowired
  private NewResourceLeftoverValidator leftoverValidator;

  @Override
  public ResourceDTO createResource(final ResourceDTO resource) {
    checkArgument(resource.getId() == null, "New resource should not have Id");
    leftoverValidator.validate(resource);

    return null;
  }
}
