import { LocalDate } from "@js-joda/core";
import { generateDummyData } from "./dummy-data";
import { ResourcesResponse } from "./model/resources-response"

/**
 * This is the main function to retrieve data from backend. 
 * 
 * @param dateStart Start date
 * @param dateEnd End date
 * @returns Promise with records
 */
export const getResources = (dateStart: LocalDate, dateEnd: LocalDate): Promise<ResourcesResponse> => {
    return new Promise((resolve) => {
        const response: ResourcesResponse = {
            dateStart: dateStart, 
            dateEnd: dateEnd, 
            resources: generateDummyData(dateStart, dateEnd)
        };

        // to simulate some delay from backend
        setTimeout(() => {
            resolve(response); 
        }, 20);
    });
}; 