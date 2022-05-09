import { LocalDate } from "@js-joda/core";
import { Optional } from "typescript-optional";
import { Amount } from "./amount";
import { ConsumptionUnit } from "./consumption-unit";
import { DayRecord } from "./day-record";

/**
 * Consignment of a particular resource.
 */
export class Consignment {
  readonly id: string;
  readonly name: string;
  readonly unit: ConsumptionUnit;
  readonly records: DayRecord[];

  constructor(
    id: string,
    name: string,
    unit: ConsumptionUnit,
    records: DayRecord[]
  ) {
    this.id = id;
    this.name = name;
    this.unit = unit;
    this.records = records;
  }

  public getLeftover(date: LocalDate): Amount {
    return this.getDayRecord(date)
      .map((day) => day.leftover)
      .orElseGet(() => Amount.of(0, this.unit));
  }

  public getDayRecord(date: LocalDate): Optional<DayRecord> {
    const found = this.records.filter((r) => r.date.isEqual(date));
    if (found.length === 0) {
      return Optional.empty();
    }
    return Optional.of(found[0]);
  }
}
