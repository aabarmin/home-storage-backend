import { LocalDate } from "@js-joda/core";
import { Consignment } from "../../../model/consignment";
import { DayRecord } from "../../../model/day-record";

export interface DayRecordResponse {
    date: LocalDate, 
    consignment: Consignment, 
    record: DayRecord
}