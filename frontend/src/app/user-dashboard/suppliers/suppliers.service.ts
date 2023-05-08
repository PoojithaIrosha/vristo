import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {shareReplay} from "rxjs";
import {CompanyDTO} from "../../dto/CompanyDTO";
import {SupplierDTO} from "../../dto/SupplierDTO";
import {ClientResponse} from "../../dto/ClientResponse";

@Injectable({
    providedIn: 'root'
})
export class SuppliersService {

    constructor(private http: HttpClient) {
    }

    searchSuppliers(text: string, page: number, size: number) {
        return this.http.get<any>(`http://localhost:8080/api/suppliers/search?text=${text}&page=${page}&size=${size}`).pipe(
            shareReplay(1)
        );
    }

    getSupplierById(id: string) {
        return this.http.get<any>(`http://localhost:8080/api/suppliers/${id}`);
    }

    getAllCompanies() {
        return this.http.get<CompanyDTO[]>(`http://localhost:8080/api/companies`).pipe(
            shareReplay(1)
        );
    }

    registerSupplier(supplier: SupplierDTO) {
        console.log(supplier)
        return this.http.post<ClientResponse>(`http://localhost:8080/api/suppliers`, supplier);
    }

    updateSupplier(supplier: { id: number; name: string; email: string; mobile: string; company: CompanyDTO; }) {
        return this.http.put(`http://localhost:8080/api/suppliers/update`, supplier);
    }
}
