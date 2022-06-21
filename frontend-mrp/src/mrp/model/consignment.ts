import { LocalDate } from "@js-joda/core";
import { Optional } from "typescript-optional";
import { Amount } from "./amount";
import { ConsumptionUnit } from "./consumption-unit";
import { DayRecord } from "./day-record";

/**
 * Basic data model of a consignment.
 */
export interface Consignment {
  consignmentId: string;
  resourceId: string;
  name: string;
  unit: ConsumptionUnit;
}

/**
 * Consignment of a particular resource.
 */
export interface ConsignmentWithResources extends Consignment {
  records: DayRecord[];
}

/**
 * Consignment of a particular resource with leftover.
 */
export interface ConsignmentWithLeftovers extends Consignment {
  leftover: Amount;
}

/**
 * Get supply for particular date.
 * @param consignment to retrieve
 * @param date
 * @returns
 */
export const getSupply = (
  consignment: ConsignmentWithResources,
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
  consignment: ConsignmentWithResources,
  date: LocalDate
): Amount => {
  return getDayRecord(consignment, date)
    .map((record: DayRecord) => record.consumption)
    .orElseGet(() => Amount.of(0, consignment.unit));
};

export const getLeftover = (
  consignment: ConsignmentWithResources,
  date: LocalDate
): Amount => {
  return getDayRecord(consignment, date)
    .map((day) => day.leftover)
    .orElseGet(() => Amount.of(0, consignment.unit));
};

export const getDayRecord = (
  consignment: ConsignmentWithResources,
  date: LocalDate
): Optional<DayRecord> => {
  const found = consignment.records.filter((r) => r.date.isEqual(date));
  if (found.length === 0) {
    return Optional.empty();
  }
  return Optional.of(found[0]);
};
