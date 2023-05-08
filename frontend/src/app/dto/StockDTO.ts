export interface StockDTO {
    productName: string | null | undefined;
    brandId: string | null | undefined;
    categoryId: string | null | undefined;
    sellingPriceFrom: number | null | undefined;
    sellingPriceTo: number | null | undefined;
    manufactureDateFrom: Date | null | undefined;
    manufactureDateTo: Date | null | undefined;
    expireDateFrom: Date | null | undefined;
    expireDateTo: Date | null | undefined;
}