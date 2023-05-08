import {Injectable} from '@angular/core';
import {Grn} from "../../dto/GrnDTO";
import {HttpClient} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class GrnService {

    constructor(private http: HttpClient) {
    }

    addGrn(grn: Grn) {
        return this.http.post("http://localhost:8080/api/grn", grn, {responseType: 'blob'});
    }

    searchGrns(data: { page: number, size: number, startDate: Date, endDate: Date }) {
        return this.http.post<any>("http://localhost:8080/api/grn/search", {
            page: data.page,
            size: data.size,
            startDate: data.startDate,
            endDate: data.endDate
        });
    }

    generateReport(grnId: number) {
        return this.http.get(`http://localhost:8080/api/grn/report/${grnId}`, {responseType: 'blob'});
    }
}
