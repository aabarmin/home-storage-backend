import { LocalDate } from "@js-joda/core";
import { Resource } from "../../../model/resource";

export interface ResourcesResponse {
    dateStart: LocalDate; 
    dateEnd: LocalDate; 
    resources: Resource[];
}