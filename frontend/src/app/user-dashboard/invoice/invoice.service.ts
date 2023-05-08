import {Injectable} from '@angular/core';
import {Invoice} from "../../dto/InvoiceDTO";
import {HttpClient} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class InvoiceService {

    constructor(private http: HttpClient) {
    }

    addInvoice(invoice: Invoice) {
        return this.http.post("http://localhost:8080/api/invoice", invoice, {responseType: 'blob'});
    }

    searchInvoices(data: { page: number, size: number, startDate: Date, endDate: Date }) {
        return this.http.post<any>("http://localhost:8080/api/invoice/search", {
            page: data.page,
            size: data.size,
            startDate: data.startDate,
            endDate: data.endDate
        });
    }

    generateReport(invoiceId: number) {
        return this.http.get("http://localhost:8080/api/invoice/report/" + invoiceId, {responseType: 'blob'})

    }
}
