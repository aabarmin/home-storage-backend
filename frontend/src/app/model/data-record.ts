import { Device } from "./device";
import { FileId } from "./file-id";
import { Flat } from "./flat";

export interface DataRecord {
    date: Date
    flat: Flat
    device: Device
    reading: number
    invoiceFile?: FileId
    receiptFile?: FileId
}
