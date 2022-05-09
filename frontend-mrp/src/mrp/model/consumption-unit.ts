import { ConsumptionType } from "./consumption-type";

/**
 * Measurement unit like kg. 
 */
export class ConsumptionUnit {
    readonly name: string; 
    readonly alias: string; 
    readonly consumptionType: ConsumptionType; 

    constructor(name: string, alias: string, consumptionType: ConsumptionType) {
        this.name = name; 
        this.alias = alias; 
        this.consumptionType = consumptionType;
    }
}