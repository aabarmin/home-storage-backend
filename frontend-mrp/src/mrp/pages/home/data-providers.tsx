import { LocalDate, Month } from "@js-joda/core";
import { Consignment } from "../../model/consignment";
import { DayRecord } from "../../model/day-record";
import { ResourceWithConsignments } from "../../model/resource";
import { generateDummyData } from "./dummy-data";
import { DayRecordResponse } from "./model/day-record-response";
import { ResourcesResponse } from "./model/resources-response";

const dummyData: ResourceWithConsignments[] = generateDummyData(LocalDate.of(2020, Month.APRIL, 1), LocalDate.of(2020, Month.APRIL, 30));

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
            resources: dummyData
        };

        // to simulate some delay from backend
        setTimeout(() => {
            resolve(response); 
        }, 20);
    });
}; 

export const getDayRecord = (consignmentId: string, date: LocalDate): Promise<DayRecordResponse> => {
    let consignment: Consignment | null = null;
    let dayRecord: DayRecord | null = null;
    dummyData.forEach((record: ResourceWithConsignments) => {
        const filteredConsignments = record.consignments.filter(c => c.consignmentId === consignmentId);
        if (filteredConsignments.length === 1) {
            consignment = filteredConsignments[0];
            const filteredRecords = filteredConsignments[0].records.filter(r => r.date.isEqual(date));
            if (filteredRecords.length === 1) {
                dayRecord = filteredRecords[0];
            }
        }
    });

    return new Promise(resolve => {
        setTimeout(() => {
            resolve({
                date: date, 
                consignment: consignment as Consignment, 
                record: dayRecord as DayRecord
            });
        }, 500);
    });
}

export const patchDayRecord = (record: DayRecord): Promise<void> => {
    dummyData.forEach(resource => {
        resource.consignments.forEach(c => {
            c.records.filter(r => r.recordId === record.recordId)
                    .forEach(r => {
                        r.consumption = record.consumption;
                        r.supply = record.supply;
                    });
        });
    });

    return new Promise(resolve => {
        setTimeout(() => {
            resolve();
        }, 500);
    });
};