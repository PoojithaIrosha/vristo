import {Component, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {ProductsService} from "../products.service";

@Component({
    selector: 'app-all-products',
    templateUrl: './all-products.component.html',
    styleUrls: ['./all-products.component.css']
})
export class AllProductsComponent implements OnInit {
    productList!: {
        id: number;
        name: string;
        brand: {
            id: number;
            name: string;
            category: {
                id: number;
                name: string;
            }
        },
        unit: {
            id: number;
            name: string;
        }
    }[];

    totalPages: number[] = [];
    totalElements!: number;
    currentPage: number = 0;
    sizesList: number[] = [10, 20, 30, 40, 50]
    sizeNum: number = this.sizesList[0];
    last: boolean = false;
    offset!: number;

    searchForm = this.formBuilder.group({
        search: ['']
    });

    constructor(private formBuilder: FormBuilder, private productsService: ProductsService
    ) {
    }

    ngOnInit() {
        this.searchProducts(0, this.sizeNum);
    }

    searchProducts(page: number, size: number) {
        let searchText = '';
        if (this.searchForm.controls.search.value) {
            searchText = this.searchForm.controls.search.value;
        }

        this.productsService.searchProducts(page, size, searchText).subscribe({
            next: data => {
                this.totalPages = [];
                for (let i = 1; i <= data.totalPages; i++) {
                    this.totalPages[i - 1] = i;
                }
                this.currentPage = data.pageable.pageNumber;
                this.totalElements = data.totalElements;
                this.last = data.last;
                this.offset = data.pageable.offset;
                this.productList = data.content;
            },
            error: err => {
                console.log(err.error)
            }
        })
    }

    paginate(page: number) {
        this.searchProducts(page, this.sizeNum);
    }

    sizeChange(size: string) {
        this.sizeNum = parseInt(size);
        this.paginate(this.currentPage);
    }

}
