export interface Device {
  id: number;
  title: string;
  alias: string;
  flatId: number;

  needInvoices: boolean;
  needReceipts: boolean;
  needReadings: boolean;
}
