import { LocalDate } from "@js-joda/core";
import { generateDummyData } from "./dummy-data";
import { Response } from "./model/response"

/**
 * This is the main function to retrieve data from backend. 
 * 
 * @param dateStart Start date
 * @param dateEnd End date
 * @returns Promise with records
 */
export const getRecords = (dateStart: LocalDate, dateEnd: LocalDate): Promise<Response> => {
    return new Promise((resolve) => {
        const response: Response = {
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