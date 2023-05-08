import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {shareReplay} from "rxjs";
import {CompanyDTO} from "../../../dto/CompanyDTO";
import {ClientResponse} from "../../../dto/ClientResponse";

@Injectable({
    providedIn: 'root'
})
export class CompanyService {

    constructor(private http: HttpClient) {
    }

    searchCompanies(text: string, page: number, size: number) {
        return this.http.get<any>(`http://localhost:8080/api/companies/search?text=${text}&page=${page}&size=${size}`).pipe(
            shareReplay(1)
        );
    }

    registerCompany(company: { name: string, email: string, mobile: string, address: string }) {
        return this.http.post<ClientResponse>(`http://localhost:8080/api/companies`, company)
    }

    getCompany(companyId: string) {
        return this.http.get<CompanyDTO>(`http://localhost:8080/api/companies/${companyId}`)
    }

    updateCompany(company: CompanyDTO) {
        return this.http.put<ClientResponse>(`http://localhost:8080/api/companies`, company)
    }
}
