import { DayRecord } from "./day-record";

/**
 * Consignment of a particular resource.
 */
export interface Consignment {
  id: string;
  name: string;
  records: DayRecord[];
}
