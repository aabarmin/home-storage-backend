export interface DataRecord {
    date: Date
    flat: string
    device: string
    reading: number
    invoiceFile?: string | null
    receiptFile?: string | null
}
