import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ClientResponse} from "../../dto/ClientResponse";
import {shareReplay} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class BrandsService {

    constructor(private http: HttpClient) {
    }

    getBrands() {
        return this.http.get("http://localhost:8080/api/brands").pipe(shareReplay(1));
    }


    getBrand(brandId: number) {
        return this.http.get<any>(`http://localhost:8080/api/brands/${brandId}`);
    }

    searchBrands(page: number, size: number, searchText: string) {
        return this.http.get<any>(`http://localhost:8080/api/brands/search?page=${page}&size=${size}&text=${searchText}`).pipe(shareReplay(1));
    }

    getCategories() {
        return this.http.get<{ id: number, name: string }>('http://localhost:8080/api/categories').pipe(shareReplay(1));
    }

    createBrand(brand: {
        name: string,
        category: { id: number, name: string }
    }) {
        return this.http.post<ClientResponse>('http://localhost:8080/api/brands', brand);
    }

    updateBrand(brand: {
        id: number,
        name: string,
        category: { id: number, name: string }
    }) {
        return this.http.put<ClientResponse>('http://localhost:8080/api/brands', brand);
    }
}
