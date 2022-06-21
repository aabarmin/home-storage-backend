import { ConsumptionType } from "./consumption-type";

/**
 * Measurement unit like kg.
 */
export class ConsumptionUnit {
  readonly name: string;
  readonly nameShort: string;
  readonly alias: string;
  readonly consumptionType: ConsumptionType;

  constructor(
    name: string,
    nameShort: string,
    alias: string,
    consumptionType: ConsumptionType
  ) {
    this.name = name;
    this.nameShort = nameShort;
    this.alias = alias;
    this.consumptionType = consumptionType;
  }
}
