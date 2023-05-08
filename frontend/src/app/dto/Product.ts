export interface Product {
    id: number;
    name: string;
    brand: {
        id: number;
        name: string;
        category: {
            id: number;
            name: string;
        };
    };
    unit: {
        id: number;
        name: string;
    }
}