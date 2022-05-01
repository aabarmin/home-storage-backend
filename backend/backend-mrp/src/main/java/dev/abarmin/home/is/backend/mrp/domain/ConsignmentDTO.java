package dev.abarmin.home.is.backend.mrp.domain;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeMultimap;
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
  @Getter(AccessLevel.NONE)
  private final Multimap<LocalDate, ConsumptionDTO> consumptions = TreeMultimap.create();

  /**
   * Information about supplies.
   */
  @Valid
  @Getter(AccessLevel.NONE)
  private final Multimap<LocalDate, SupplyDTO> supplies = TreeMultimap.create();

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
   * Get consumptions created for the given date.
   *
   * @param date
   * @return
   */
  public Collection<ConsumptionDTO> getConsumptionCreatedAt(final LocalDate date) {
    return consumptions.get(date);
  }

  /**
   * Get supply created for the given date.
   *
   * @param date
   * @return
   */
  public Collection<SupplyDTO> getSupplyCreatedAt(final LocalDate date) {
    return supplies.get(date);
  }

  /**
   * Convenient method in order not to convert LocalDateTime to LocalDate every time.
   * @param dateTime
   * @return
   */
  public Collection<SupplyDTO> getSupplyCreatedAt(final LocalDateTime dateTime) {
    return getSupplyCreatedAt(dateTime.toLocalDate());
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
   * Get all consumptions.
   *
   * @return
   */
  public Collection<ConsumptionDTO> getConsumptions() {
    return Collections.unmodifiableCollection(consumptions.values());
  }

  /**
   * Get all supplies.
   *
   * @return
   */
  public Collection<SupplyDTO> getSupplies() {
    return Collections.unmodifiableCollection(supplies.values());
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
   * Add consumption to the consignment.
   * @param consumptionDTO
   */
  public void addConsumption(final ConsumptionDTO consumptionDTO) {
    checkArgument(
        getMeasureUnit().equals(consumptionDTO.getAmount().getUnit()),
        "Current consignment and consumption have different measurement units"
    );

    consumptionDTO.setConsignment(this);
    this.consumptions.put(
        consumptionDTO.getCreatedAt().toLocalDate(),
        consumptionDTO
    );
  }

  /**
   * Add supply to the consignment. 
   * @param supplyDTO
   */
  public void addSupply(final SupplyDTO supplyDTO) {
    checkArgument(
        getMeasureUnit().equals(supplyDTO.getAmount().getUnit()),
        "Current consignment and supply have different measurement units"
    );

    supplyDTO.setConsignment(this);
    this.supplies.put(
        supplyDTO.getCreatedAt().toLocalDate(),
        supplyDTO
    );
  }
}
