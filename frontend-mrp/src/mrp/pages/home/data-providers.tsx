import { LocalDate, Month } from "@js-joda/core";
import { Amount } from "../../model/amount";
import { Consignment, ConsignmentWithLeftovers, ConsignmentWithResources, getLeftover } from "../../model/consignment";
import { DayRecord } from "../../model/day-record";
import { ResourceWithConsignments, ResourceWithLeftovers } from "../../model/resource";
import { ResourceListResponse } from "../resources/model/resource-list-response";
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
        }, 50);
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
        }, 50);
    });
};

/**
 * Used to retrieve a list of available resources
 * @returns A list of resources
 */
export const getResourcesList = (): Promise<ResourceListResponse> => {
    const calculateLeftover = (c: ConsignmentWithResources): Amount => {
        return getLeftover(c, LocalDate.of(2020, Month.APRIL, 30));
    };

    const resources: ResourceWithLeftovers[] = dummyData.map(r => {
        return {
            name: r.name, 
            resourceId: r.resourceId, 
            consignments: r.consignments.map(c => {
                return {
                    consignmentId: c.consignmentId, 
                    resourceId: c.resourceId, 
                    name: c.name, 
                    unit: c.unit, 
                    leftover: calculateLeftover(c)
                } as ConsignmentWithLeftovers;
            })
        } as ResourceWithLeftovers;
    });

    return new Promise(resolve => {
        setTimeout(() => {
            resolve({resources});
        }, 50)
    });
};