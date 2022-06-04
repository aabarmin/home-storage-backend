import { LocalDate } from "@js-joda/core";
import { ConsignmentWithResources } from "../../../model/consignment";
import { DayRecord } from "../../../model/day-record";

export interface ConsumptionResponse {
    date: LocalDate, 
    consignment: ConsignmentWithResources, 
    record: DayRecord
}