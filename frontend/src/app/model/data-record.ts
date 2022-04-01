export interface DataRecord {
  id?: string;
  date: Date;
  flatId: number;
  deviceId: number;
  reading: number;
  invoiceFileId?: number | null;
  receiptFileId?: number | null;
}
