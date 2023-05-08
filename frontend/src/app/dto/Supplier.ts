export interface Supplier {
    id: number;
    name: string;
    email: string;
    mobile: string;
    company: {
        id: number;
        name: string;
        email: string;
        mobile: string;
        address: string;
    }
}