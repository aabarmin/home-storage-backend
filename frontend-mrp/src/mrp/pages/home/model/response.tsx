import { LocalDate } from "@js-joda/core";

export interface Response {
    dateStart: LocalDate; 
    dateEnd: LocalDate; 
    resources: Resource[];
}

export interface DayRecord {
    id: string; 
    date: LocalDate; 
    leftover: number; 
    supply: number; 
    consumption: number; 
}

export interface Consignment {
    id: string; 
    name: string; 
    records: DayRecord[];
}

export interface Resource {
    id: string; 
    name: string; 
    consignments: Consignment[];
}