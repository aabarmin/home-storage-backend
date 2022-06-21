import { LocalDate, Month } from "@js-joda/core";
import { Amount } from "../model/amount";
import { Consignment, ConsignmentWithLeftovers, ConsignmentWithResources, getLeftover } from "../model/consignment";
import { DayRecord } from "../model/day-record";
import { Resource, ResourceWithConsignments, ResourceWithLeftovers } from "../model/resource";
import { ResourceListResponse } from "../pages/resources/model/resource-list-response";
import { dummyConsumptionUnit, generateDummyData } from "../pages/home/dummy-data";
import { DayRecordResponse } from "../pages/home/model/day-record-response";
import { ResourcesResponse } from "../pages/home/model/resources-response";
import { v4 as uuid } from 'uuid';
import { ConsumptionUnit } from "../model/consumption-unit";
import { findFirst, hasItem, replaceFirst } from "../utils/array-utils";

const startDate = LocalDate.now().minusMonths(1);
const endDate = LocalDate.now().plusMonths(1);
const dummyData: ResourceWithConsignments[] = generateDummyData(startDate, endDate);

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

/**
 * Get a resource by id. 
 * @param resourceId 
 * @returns 
 */
export const getResource = (resourceId: string): Promise<Resource> => {
    return new Promise(resolve => {
        const found = dummyData.filter(r => r.resourceId === resourceId);
        setTimeout(() => {
            resolve(found[0])
        }, 50);
    });
};

/**
 * Create new or update existing resource. 
 * @param resource to create or update
 * @returns 
 */
export const saveResource = (resource: Resource): Promise<ResourceWithLeftovers> => {
    // dummy implementation, should be handled by backend
    const newResource: ResourceWithLeftovers = resource.resourceId === '' ? 
        {
            name: resource.name, 
            resourceId: uuid(), 
            consignments: []
        } : 
        {
            name: resource.name, 
            resourceId: resource.resourceId, 
            consignments: []
        }

    // actually, need to add to the dummy data
    const filtered = dummyData.filter(resource => resource.resourceId === newResource.resourceId);
    if (filtered.length === 0) {
        dummyData.push({
            name: newResource.name, 
            resourceId: newResource.resourceId, 
            consignments: []
        });
    } else {
        const index = dummyData.indexOf(filtered[0]);
        dummyData[index] = {
            name: newResource.name, 
            resourceId: newResource.resourceId, 
            consignments: filtered[0].consignments
        };
    }

    return new Promise(resolve => {
        setTimeout(() => {
            resolve(newResource);
        }, 50);
    });
}

export const Consignments = {
    getConsignment: (resourceId: string, consignmentId: string): Promise<Consignment> => {
        const resource = findFirst(dummyData, (r) => {
            return r.resourceId === resourceId;
        })
        const consignment = findFirst(resource.consignments, (c) => {
            return c.consignmentId === consignmentId;
        });
        return new Promise(resolve => {
            setTimeout(() => {
                resolve(consignment); 
            }, 50);
        });
    }, 
    createConsignment: (resourceId: string): Promise<Consignment> => {
        return new Promise(resolve => {
            resolve({
                name: '', 
                consignmentId: uuid(),
                resourceId: resourceId, 
                unit: dummyConsumptionUnit // TODO: fixit, there should be a real unit
            });
        });
    },
    saveConsignment: (consignment: Consignment): Promise<Consignment> => {
        return new Promise(resolve => {
            const resource = findFirst(dummyData, (r) => { 
                return r.resourceId === consignment.resourceId 
            });
            const alreadyExists = hasItem(resource.consignments, (c) => {
                return c.consignmentId === consignment.consignmentId;
            });
            if (alreadyExists) {
                const existingConsignment: ConsignmentWithResources = findFirst(resource.consignments, (c) => {
                    return c.consignmentId === consignment.consignmentId;
                });
                existingConsignment.name = consignment.name;
                existingConsignment.unit = consignment.unit;

                replaceFirst(resource.consignments, existingConsignment, (c) => {
                    return c.consignmentId === consignment.consignmentId;
                });

                resolve(existingConsignment);
            } else {
                const newConsignment: ConsignmentWithResources = {
                    name: consignment.name, 
                    resourceId: consignment.resourceId, 
                    consignmentId: consignment.consignmentId, 
                    unit: consignment.unit, 
                    records: []
                };
                resource.consignments.push(newConsignment);

                resolve(newConsignment); 
            }
        });
    }
}

export const getConsumptionUnits = (): Promise<ConsumptionUnit[]> => {
    return new Promise(resolve => {
        setTimeout(() => {
            resolve([
                dummyConsumptionUnit
            ]);
        }, 50);
    });
}