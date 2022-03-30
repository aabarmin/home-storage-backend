export interface DataRecord {
  id: string;
  date: Date;
  flat: string;
  device: string;
  reading: number;
  invoiceFile?: string | null;
  receiptFile?: string | null;
}
