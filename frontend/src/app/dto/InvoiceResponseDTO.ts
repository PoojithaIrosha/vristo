export interface InvoiceResponse {
    id: number
    dateTime: string
    user: User
    invoiceItems: InvoiceItem[]
    invoicePayment: InvoicePayment
}

export interface User {
    id: number
    name: string
    mobile: string
    email: string
    createdAt: string
    role: string
    isEnabled: boolean
}

export interface InvoiceItem {
    id: number
    quantity: number
    stock: Stock
}

export interface Stock {
    id: number
    quantity: number
    sellingPrice: number
    manufactureDate: string
    expireDate: string
    product: Product
}

export interface Product {
    id: number
    name: string
    brand: Brand
    unit: Unit
}

export interface Brand {
    id: number
    name: string
    category: Category
}

export interface Category {
    id: number
    name: string
}

export interface Unit {
    id: number
    name: string
}

export interface InvoicePayment {
    id: number
    payment: number
    balance: number
    paymentType: string
}
