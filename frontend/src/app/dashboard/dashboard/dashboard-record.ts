import { DataRecord } from 'src/app/model/data-record';
import { Device } from 'src/app/model/device';
import { FileId } from 'src/app/model/file-id';
import { Flat } from 'src/app/model/flat';

export class DashboardRecord {
  month: string;
  records: DashboardDataRecord[] = [];

  constructor(month: string) {
    this.month = month;
  }

  public addRecord(record: DashboardDataRecord): void {
    this.records.push(record);
  }
}

export class DashboardDataRecord {
  constructor(
    public record: DataRecord,
    public device: Device,
    public flat: Flat,
    public invoiceFile: FileId | null,
    public receiptFile: FileId | null
  ) {}
}
