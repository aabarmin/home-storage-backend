export interface DataRecord {
  id: number;
  date: Date;
  flat: String;
  device: string;
  reading: number;
  invoiceFile?: string | null;
  receiptFile?: string | null;
}
