import { v4 as uuid } from 'uuid';
import { LocalDate } from "@js-joda/core";
import { Chance } from 'chance';
import { ConsignmentWithResources } from '../../model/consignment';
import { DayRecord } from '../../model/day-record';
import { ResourceWithConsignments } from '../../model/resource';
import { ConsumptionUnit } from '../../model/consumption-unit';
import { ConsumptionType } from '../../model/consumption-type';
import { Amount } from '../../model/amount';

const chance = new Chance();

const dummyConsumptionUnit: ConsumptionUnit = new ConsumptionUnit(
    "Kilogram", "kg", "kg", ConsumptionType.UNIT_CONSUMPTION
);

const generateDummyResource = (name: string, consignments: ConsignmentWithResources[]): ResourceWithConsignments => {
    const resourceId = uuid()

    consignments.forEach(c => c.resourceId = resourceId);

    return {
        resourceId: resourceId, 
        name: name, 
        consignments: consignments
    };
};

const generateConsignment = (name: string, records: DayRecord[]): ConsignmentWithResources => {
    const consignmentId = uuid();
    records.forEach(r => r.consignmentId = consignmentId);

    return {
        consignmentId: consignmentId, 
        resourceId: 'temporaryValue',
        name: name, 
        unit: dummyConsumptionUnit, 
        records: records
    };
};

const generateDayRecords = (dateStart: LocalDate, dateEnd: LocalDate): DayRecord[] => {
    const result: DayRecord[] = [];
    let currentDate = dateStart; 
    let currentValue = chance.integer({
        min: 0, 
        max: 100
    }); 
    while (currentDate.isBefore(dateEnd) || currentDate.isEqual(dateEnd)) {
        const supply = chance.integer({
            min: 0, max: 5
        });
        const consume = chance.integer({
            min: 0, max: 5
        });
        currentValue = currentValue + supply - consume; 
        if (currentValue < 0) {
            currentValue = 0; 
        }
        result.push({
            recordId: uuid(), 
            consignmentId: '', 
            date: currentDate, 
            supply: Amount.of(supply, dummyConsumptionUnit), 
            consumption: Amount.of(consume, dummyConsumptionUnit), 
            leftover: Amount.of(currentValue, dummyConsumptionUnit)
        });
        currentDate = currentDate.plusDays(1); 
    }
    return result; 
};

export const generateDummyData = (dateStart: LocalDate, dateEnd: LocalDate): ResourceWithConsignments[] => {
    return [
        generateDummyResource("Морковь", [
            generateConsignment("В пакете", generateDayRecords(dateStart, dateEnd)),
            generateConsignment("Тертая", generateDayRecords(dateStart, dateEnd))
        ]), 
        generateDummyResource("Картошка", []), 
        generateDummyResource("Рыба", []), 
        generateDummyResource("Коржачий корм", [
            generateConsignment("С рыбой", generateDayRecords(dateStart, dateEnd)),
            generateConsignment("С олениной", generateDayRecords(dateStart, dateEnd))
        ])
    ];
};