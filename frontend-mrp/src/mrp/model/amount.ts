import { ConsumptionUnit } from "./consumption-unit";

/**
 * Amount of any resource either leftover, consumption or supply.
 */
export class Amount {
  readonly amount: number;
  readonly unit: ConsumptionUnit;

  constructor(amount: number, unit: ConsumptionUnit) {
    this.amount = amount;
    this.unit = unit;
  }

  static of(amount: number, unit: ConsumptionUnit): Amount {
    return new Amount(amount, unit);
  }

  public plus(amount: Amount): Amount {
    return Amount.of(this.amount + amount.amount, this.unit);
  }
}
