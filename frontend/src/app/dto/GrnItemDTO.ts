import {Product} from "./Product";

export interface GrnItemDTO {
    product: Product;
    quantity: number;
    buyingPrice: number;
    sellingPrice: number;
    manufactureDate: Date;
    expireDate: Date;
    total: number;
}