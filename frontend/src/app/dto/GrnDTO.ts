import {Supplier} from "./Supplier";
import {Product} from "./Product";
import {PaymentTypes} from "./PaymentTypes";

export interface Grn {
    supplier: Supplier
    grnPayment: GrnPayment
    grnItems: GrnItem[]
}

export interface GrnPayment {
    payment: number
    balance: number
    paymentType: PaymentTypes
}

export interface GrnItem {
    product: Product
    quantity: number
    buyingPrice: number
    sellingPrice: number
    manufactureDate: Date
    expireDate: Date
}
