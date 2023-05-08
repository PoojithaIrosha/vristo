import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {shareReplay} from "rxjs";
import {ClientResponse} from "../../dto/ClientResponse";

@Injectable({
    providedIn: 'root'
})
export class ProductsService {

    constructor(private http: HttpClient) {
    }

    searchProducts(page: number, size: number, text: string) {
        return this.http.get<any>(`http://localhost:8080/api/products/search?page=${page}&size=${size}&text=${text}`).pipe(
            shareReplay(1)
        );
    }

    getBrands() {
        return this.http.get("http://localhost:8080/api/brands").pipe(shareReplay(1));
    }

    getUnits() {
        return this.http.get("http://localhost:8080/api/units").pipe(shareReplay(1));
    }

    getProduct(id: number) {
        return this.http.get<any>(`http://localhost:8080/api/products/${id}`);
    }

    registerProduct(product: { name: string, brand: number, unit: number }) {
        return this.http.post<ClientResponse>('http://localhost:8080/api/products', product);
    }

    updateProduct(product: { id: number, name: string, brand: number, unit: number }) {
        return this.http.put<ClientResponse>('http://localhost:8080/api/products', product);
    }
}

