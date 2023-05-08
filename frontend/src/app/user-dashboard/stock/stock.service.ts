import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {StockDTO} from "../../dto/StockDTO";
import {retry, shareReplay} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class StockService {

    constructor(private http: HttpClient) {
    }

    public searchStock(stockDTO: StockDTO, page: number, size: number) {
        return this.http.post<any>("http://localhost:8080/api/stock/search", {
            stock: stockDTO,
            page: page,
            size: size
        }).pipe(shareReplay(1), retry(3));
    }

    public updateStockPrice(stockPrice: { stockId: number, newPrice: number }) {
        return this.http.post("http://localhost:8080/api/stock/update-price", stockPrice);
    }

    public generateStockReport() {
        return this.http.get("http://localhost:8080/api/stock/report", {responseType: 'blob'});
    }
}

