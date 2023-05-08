import {PaymentTypes} from "./PaymentTypes";
import {Stock} from "./GrnResponseDTO";

export interface Invoice {
    invoicePayment: InvoicePayment
    invoiceItems: InvoiceItem[]
}

export interface InvoicePayment {
    payment: number
    balance: number
    paymentType: PaymentTypes
}

export interface InvoiceItem {
    stock: Stock
    quantity: number
    sellingPrice: number
    manufactureDate: Date
    expireDate: Date
    total: number
}
