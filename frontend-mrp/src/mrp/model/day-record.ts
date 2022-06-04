import { LocalDate } from "@js-joda/core";
import { Amount } from "./amount";

/**
 * Record about a resource within a particular day.
 */
export interface DayRecord {
  id: string;
  date: LocalDate;
  leftover: Amount;
  supply: Amount;
  consumption: Amount;
}
