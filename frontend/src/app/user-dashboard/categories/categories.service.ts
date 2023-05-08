import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {shareReplay} from "rxjs";
import {ClientResponse} from "../../dto/ClientResponse";

@Injectable({
    providedIn: 'root'
})
export class CategoriesService {

    constructor(private http: HttpClient) {
    }

    searchCategories(page: number, size: number, searchText: string) {
        return this.http.get<any>(`http://localhost:8080/api/categories/search?page=${page}&size=${size}&text=${searchText}`).pipe(shareReplay(1));
    }

    getCategoryById(categoryId: number) {
        return this.http.get<any>(`http://localhost:8080/api/categories/${categoryId}`).pipe(shareReplay(1));
    }

    registerCategory(category: {
        name: string;
    }) {
        return this.http.post<ClientResponse>(`http://localhost:8080/api/categories`, category);
    }

    editCategory(category: {
        id: number,
        name: string;
    }) {
        return this.http.put<ClientResponse>(`http://localhost:8080/api/categories`, category);
    }

}
