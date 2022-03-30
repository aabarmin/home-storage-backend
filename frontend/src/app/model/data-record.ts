export interface DataRecord {
  id?: string;
  date: Date;
  flatId: number;
  deviceId: number;
  reading: number;
  invoiceFile?: number | null;
  receiptFile?: number | null;
}
