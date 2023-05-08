import {Component, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {BrandsService} from "../brands/brands.service";
import {Stock} from "../../dto/GrnResponseDTO";
import {StockService} from "./stock.service";
import {StockDTO} from "../../dto/StockDTO";
import Swal from "sweetalert2";

@Component({
    selector: 'app-stock',
    templateUrl: './stock.component.html',
    styleUrls: ['./stock.component.css']
})
export class StockComponent implements OnInit {

    isVisible: boolean = false;
    selectedStock!: Stock;

    stockList!: Stock[];
    brands: any;
    categories: any;
    totalPages: number[] = [];
    totalElements!: number;
    currentPage: number = 0;
    sizesList: number[] = [10, 20, 30, 40, 50]
    sizeNum: number = this.sizesList[0];
    last: boolean = false;
    offset!: number;

    constructor(private formBuilder: FormBuilder, private brandsService: BrandsService, private stockService: StockService) {
        this.brandsService.getBrands().subscribe((data: any) => {
            this.brands = data;
        });
        this.brandsService.getCategories().subscribe((data: any) => {
            this.categories = data;
        });
    }

    ngOnInit() {
        this.searchStock(0, this.sizeNum);
    }

    stockForm = this.formBuilder.group({
        productName: [null],
        brandId: [''],
        categoryId: [''],
        sellingPriceFrom: [null],
        sellingPriceTo: [null],
        manufactureDateFrom: [null],
        manufactureDateTo: [null],
        expireDateFrom: [null],
        expireDateTo: [null],
    });

    searchStock(page: number, size: number) {

        let productName = this.stockForm.get("productName")?.value;
        let brandId = this.stockForm.get("brandId")?.value == '' ? null : this.stockForm.get("brandId")?.value;
        let categoryId = this.stockForm.get("categoryId")?.value == '' ? null : this.stockForm.get("categoryId")?.value;
        let sellingPriceFrom = this.stockForm.get("sellingPriceFrom")?.value;
        let sellingPriceTo = this.stockForm.get("sellingPriceTo")?.value;
        let manufactureDateFrom = this.stockForm.get("manufactureDateFrom")?.value;
        let manufactureDateTo = this.stockForm.get("manufactureDateTo")?.value;
        let expireDateFrom = this.stockForm.get("expireDateFrom")?.value;
        let expireDateTo = this.stockForm.get("expireDateTo")?.value;

        let data: StockDTO = {
            productName: productName,
            brandId: brandId,
            categoryId: categoryId,
            sellingPriceFrom: sellingPriceFrom,
            sellingPriceTo: sellingPriceTo,
            manufactureDateFrom: manufactureDateFrom,
            manufactureDateTo: manufactureDateTo,
            expireDateFrom: expireDateFrom,
            expireDateTo: expireDateTo,
        }

        console.log(JSON.stringify(data));
        this.stockService.searchStock(data, page, size).subscribe({
            next: data => {
                this.stockList = [];
                this.totalPages = [];
                for (let i = 1; i <= data.totalPages; i++) {
                    this.totalPages[i - 1] = i;
                }
                this.currentPage = data.pageable.pageNumber;
                this.totalElements = data.totalElements;
                this.last = data.last;
                this.offset = data.pageable.offset;
                this.stockList = data.content;
            },
            error: error => {
                console.log(error);
            }
        });

    }

    paginate(page: number) {
        this.searchStock(page, this.sizeNum);
    }

    sizeChange(size: string) {
        this.sizeNum = parseInt(size);
        this.paginate(this.currentPage);
    }

    showUpdateModal(stock: Stock) {
        this.selectedStock = stock;
        this.isVisible = true;
    }

    hideUpdateModal() {
        this.searchStock(this.currentPage, this.sizeNum);
        this.selectedStock = {} as Stock;
        this.isVisible = false;
    }

    generateStockReport() {
        this.stockService.generateStockReport().subscribe({
            next: data => {
                const blob = new Blob([data], {type: 'application/pdf'});
                const url = window.URL.createObjectURL(blob);
                window.open(url);
            },
            error: error => {
                Swal.fire({
                    title: 'Error!',
                    text: 'Something went wrong!',
                    icon: 'error',
                })
            }
        });
    }
}