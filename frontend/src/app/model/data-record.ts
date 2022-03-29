export interface DataRecord {
  id: string;
  date: Date;
  flat: String;
  device: string;
  reading: number;
  invoiceFile?: string | null;
  receiptFile?: string | null;
}
