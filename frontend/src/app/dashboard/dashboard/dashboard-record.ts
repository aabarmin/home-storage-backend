import { DataRecord } from "src/app/model/data-record";

export class DashboardRecord {
    month: string;
    records: DataRecord[] = [];

    constructor(month: string) {
        this.month = month;
    }

    public addRecord(record: DataRecord): void {
        this.records.push(record);
    }
}