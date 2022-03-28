export interface DataRecord {
    date: Date
    flat: String
    device: string
    reading: number
    invoiceFile?: string | null
    receiptFile?: string | null
}
