import { LocalDate } from "@js-joda/core";
import { Amount } from "./amount";
import { ConsignmentWithResources, getLeftover } from "./consignment";

export interface Resource {
  readonly resourceId: string;
  readonly name: string;
}

/**
 * Resource description.
 */
 export interface ResourceWithConsignments extends Resource {
  readonly consignments: ConsignmentWithResources[];
}

/**
 * This function return leftovers for a particular resource. There may be multiple
 * units so that the function returns an array.
 *
 * @param resource to get leftovers
 * @param date
 * @returns
 */
export const getLeftovers = (resource: ResourceWithConsignments, date: LocalDate): Amount[] => {
  const result: { [key: string]: Amount } = {};
  for (let i = 0; i < resource.consignments.length; i++) {
    const leftover = getLeftover(resource.consignments[i], date);
    let resultLeftover = Amount.of(0, leftover.unit);
    if (leftover.unit.alias in result) {
      resultLeftover = result[leftover.unit.alias];
    }
    resultLeftover = resultLeftover.plus(leftover);
    result[leftover.unit.alias] = resultLeftover;
  }
  return Object.values(result);
};