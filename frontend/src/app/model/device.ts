import { Flat } from "./flat";

export interface Device {
    title: String;
    alias: String;
    flat: Flat;

    needInvoices: boolean;
    needReceipts: boolean;
    needReadings: boolean;
}
