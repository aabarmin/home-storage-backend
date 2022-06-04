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
}

/**
 * Get supply for particular date.
 * @param consignment to retrieve
 * @param date
 * @returns
 */
export const getSupply = (
  consignment: Consignment,
  date: LocalDate
): Amount => {
  return getDayRecord(consignment, date)
    .map((record: DayRecord) => record.supply)
    .orElseGet(() => Amount.of(0, consignment.unit));
};

/**
 * Get consumption for particular date
 * @param consignment
 * @param date
 */
export const getConsume = (
  consignment: Consignment,
  date: LocalDate
): Amount => {
  return getDayRecord(consignment, date)
    .map((record: DayRecord) => record.consumption)
    .orElseGet(() => Amount.of(0, consignment.unit));
};

export const getLeftover = (
  consignment: Consignment,
  date: LocalDate
): Amount => {
  return getDayRecord(consignment, date)
    .map((day) => day.leftover)
    .orElseGet(() => Amount.of(0, consignment.unit));
};

export const getDayRecord = (
  consignment: Consignment,
  date: LocalDate
): Optional<DayRecord> => {
  const found = consignment.records.filter((r) => r.date.isEqual(date));
  if (found.length === 0) {
    return Optional.empty();
  }
  return Optional.of(found[0]);
};
