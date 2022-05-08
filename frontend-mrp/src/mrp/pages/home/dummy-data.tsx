import { Consignment, DayRecord, Resource } from "./model/response";
import { v4 as uuid } from 'uuid';
import { LocalDate } from "@js-joda/core";
import { Chance } from 'chance';

const chance = new Chance();

const generateDummyResource = (name: string, consignments: Consignment[]): Resource => {
    return {
        id: uuid(), 
        name: name, 
        consignments: consignments
    };
};

const generateConsignment = (name: string, records: DayRecord[]): Consignment => {
    return {
        id: uuid(), 
        name: name, 
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
            id: uuid(), 
            date: currentDate, 
            supply: supply, 
            consumption: consume, 
            leftover: currentValue
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