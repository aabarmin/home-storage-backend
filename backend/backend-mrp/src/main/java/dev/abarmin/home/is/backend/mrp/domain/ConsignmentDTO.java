package dev.abarmin.home.is.backend.mrp.domain;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.google.common.base.Preconditions.*;

/**
 * Resource's consignment.
 *
 * @author Aleksandr Barmin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {
    "resource",
    "leftovers",
    "consumptions",
    "supplies"
})
@EqualsAndHashCode(exclude = {
    "resource",
    "leftovers",
    "consumptions",
    "supplies"
})
public class ConsignmentDTO {
  /**
   * Identifier of a consignment.
   */
  private Long id;

  /**
   * Resource.
   */
  @Nullable
  private ResourceDTO resource;

  /**
   * Name of the consignment.
   */
  @NotEmpty(message = "Consignment should have name")
  @Size(max = 255)
  private String name;

  /**
   * Measure unit used in this consignment.
   */
  @NotNull(message = "Consignment should have measure unit")
  private MeasureUnitDTO measureUnit;

  /**
   * Associated leftovers.
   */
  @Valid
  @Getter(AccessLevel.NONE)
  private final TreeMap<LocalDate, LeftoverDTO> leftovers = Maps.newTreeMap();

  /**
   * Information about consumption.
   */
  @Valid
  private final Collection<ConsumptionDTO> consumptions = Sets.newTreeSet();

  /**
   * Information about supplies.
   */
  @Valid
  @Size(min = 1, message = "Consignment should have at least one supply")
  private final Collection<SupplyDTO> supplies = Sets.newTreeSet();

  /**
   * Add leftover to the consignment.
   * @param leftoverDTO
   */
  public void addLeftover(final LeftoverDTO leftoverDTO) {
    checkArgument(
        measureUnit.equals(leftoverDTO.getAmount().getUnit()),
        "Consignment and leftover have different measurement units"
    );

    leftoverDTO.setConsignment(this);
    this.leftovers.put(leftoverDTO.getCreatedAt().toLocalDate(), leftoverDTO);
  }

  /**
   * Get leftover for the given date.
   *
   * @param date
   * @return
   */
  public Optional<LeftoverDTO> getLeftoverCreatedAt(final LocalDate date) {
    return Optional.ofNullable(leftovers.get(date));
  }

  /**
   * Get all leftovers.
   *
   * @return
   */
  public Collection<LeftoverDTO> getLeftovers() {
    return Collections.unmodifiableCollection(leftovers.values());
  }

  /**
   * Get the first added leftover.
   *
   * @return
   */
  public LeftoverDTO getFirstLeftover() {
    return leftovers.firstEntry().getValue();
  }

  /**
   * Get the latest leftover.
   *
   * @return
   */
  public LeftoverDTO getLastLeftover() {
    return leftovers.lastEntry().getValue();
  }

  /**
   * Add supply to the consignment. 
   * @param supplyDTO
   */
  public void addSupply(final SupplyDTO supplyDTO) {
    supplyDTO.setConsignment(this);
    this.supplies.add(supplyDTO);
  }
}
