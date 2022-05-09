import { v4 as uuid } from 'uuid';
import { LocalDate } from "@js-joda/core";
import { Chance } from 'chance';
import { Consignment } from '../../model/consignment';
import { DayRecord } from '../../model/day-record';
import { Resource } from '../../model/resource';
import { ConsumptionUnit } from '../../model/consumption-unit';
import { ConsumptionType } from '../../model/consumption-type';
import { Amount } from '../../model/amount';

const chance = new Chance();

const dummyConsumptionUnit: ConsumptionUnit = new ConsumptionUnit(
    "Killogram", "kg", ConsumptionType.UNIT_CONSUMPTION
);

const generateDummyResource = (name: string, consignments: Consignment[]): Resource => {
    return new Resource(uuid(), name, consignments);
};

const generateConsignment = (name: string, records: DayRecord[]): Consignment => {
    return new Consignment(
        uuid(), 
        name, 
        dummyConsumptionUnit, 
        records
    );
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
            id: uuid(), 
            date: currentDate, 
            supply: [
                Amount.of(supply, dummyConsumptionUnit)
            ], 
            consumption: [
                Amount.of(consume, dummyConsumptionUnit)
            ], 
            leftover: Amount.of(currentValue, dummyConsumptionUnit)
        });
        currentDate = currentDate.plusDays(1); 
    }
    return result; 
};

export const generateDummyData = (dateStart: LocalDate, dateEnd: LocalDate): Resource[] => {
    return [
        generateDummyResource("Морковь", [
            generateConsignment("В пакете", generateDayRecords(dateStart, dateEnd)),
            generateConsignment("Тертая", generateDayRecords(dateStart, dateEnd))
        ]), 
        generateDummyResource("Картошка", []), 
        generateDummyResource("Коржачий корм", [
            generateConsignment("С рыбой", generateDayRecords(dateStart, dateEnd)),
            generateConsignment("С олениной", generateDayRecords(dateStart, dateEnd))
        ])
    ];
};