import { LocalDate } from "@js-joda/core";
import { Amount } from "./amount";
import { Consignment } from "./consignment";

/**
 * Resource description.
 */
export class Resource {
  readonly id: string;
  readonly name: string;
  readonly consignments: Consignment[];

  constructor(id: string, name: string, consignments: Consignment[]) {
    this.id = id;
    this.name = name;
    this.consignments = consignments;
  }

  /**
   * Get leftover for a particular date.
   * @param date to calculate leftover
   */
  public getLeftovers(date: LocalDate): Amount[] {
    const result: { [key: string]: Amount } = {};
    for (let i = 0; i < this.consignments.length; i++) {
      const leftover = this.consignments[i].getLeftover(date);
      let resultLeftover = Amount.of(0, leftover.unit);
      if (leftover.unit.alias in result) {
        resultLeftover = result[leftover.unit.alias];
      }
      resultLeftover = resultLeftover.plus(leftover);
      result[leftover.unit.alias] = resultLeftover;
    }
    return Object.values(result);
  }
}
