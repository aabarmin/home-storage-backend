import { LocalDate } from "@js-joda/core";
import { Amount } from "./amount";
import { Consignment } from "./consignment";

/**
 * This function return leftovers for a particular resource. There may be multiple
 * units so that the function returns an array.
 *
 * @param resource to get leftovers
 * @param date
 * @returns
 */
export const getLeftovers = (resource: Resource, date: LocalDate): Amount[] => {
  const result: { [key: string]: Amount } = {};
  for (let i = 0; i < resource.consignments.length; i++) {
    const leftover = resource.consignments[i].getLeftover(date);
    let resultLeftover = Amount.of(0, leftover.unit);
    if (leftover.unit.alias in result) {
      resultLeftover = result[leftover.unit.alias];
    }
    resultLeftover = resultLeftover.plus(leftover);
    result[leftover.unit.alias] = resultLeftover;
  }
  return Object.values(result);
};

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
}
