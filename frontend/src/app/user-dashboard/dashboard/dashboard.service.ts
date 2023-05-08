import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {shareReplay} from "rxjs";
import {Product} from "../../dto/Product";

@Injectable({
    providedIn: 'root'
})
export class DashboardService {

    constructor(private http: HttpClient) {
    }

    getTopSellingProducts() {
        return this.http.get<Product[]>('http://localhost:8080/api/invoice/top-selling').pipe(shareReplay(1));
    }
}
