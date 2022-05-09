import { LocalDate } from "@js-joda/core";
import { Resource } from "../../../model/resource";

export interface Response {
    dateStart: LocalDate; 
    dateEnd: LocalDate; 
    resources: Resource[];
}