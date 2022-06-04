import { LocalDate } from "@js-joda/core";
import { ResourceWithConsignments } from "../../../model/resource";

export interface ResourcesResponse {
    dateStart: LocalDate; 
    dateEnd: LocalDate; 
    resources: ResourceWithConsignments[];
}