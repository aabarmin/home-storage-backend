import { LocalDate } from "@js-joda/core";
import { Consignment } from "../../../model/consignment";
import { DayRecord } from "../../../model/day-record";

export interface ConsumptionResponse {
    date: LocalDate, 
    consignment: Consignment, 
    record: DayRecord
}