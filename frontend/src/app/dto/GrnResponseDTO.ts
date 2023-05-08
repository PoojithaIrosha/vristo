export interface GrnResponse {
    id: number;
    dateTime: Date;
    supplier: Supplier;
    user: User;
    grnPayment: GrnPayment;
    grnItems: GrnItem[];
}

export interface GrnItem {
    id: number;
    quantity: number;
    buyingPrice: number;
    stock: Stock;
}

export interface Stock {
    id: number;
    quantity: number;
    sellingPrice: number;
    manufactureDate: Date;
    expireDate: Date;
    product: Product;
}

export interface StockTableDTO {
    id: number;
    quantity: number;
    sellingPrice: number;
    manufactureDate: Date;
    expireDate: Date;
    product: Product;
    total: number;
}

export interface Product {
    id: number;
    name: string;
    brand: Brand;
    unit: Unit;
}

export interface Brand {
    id: number;
    name: string;
    category: Unit;
}

export interface Unit {
    id: number;
    name: string;
}

export interface GrnPayment {
    id: number;
    payment: number;
    balance: number;
    paymentType: string;
}

export interface Supplier {
    id: number;
    name: string;
    mobile: string;
    email: string;
    company?: Supplier;
    address?: string;
}

export interface User {
    id: number;
    name: string;
    mobile: string;
    email: string;
    createdAt: Date;
    role: string;
    isEnabled: boolean;
}