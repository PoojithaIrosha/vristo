import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild} from '@angular/core';
import {StockService} from "../../stock/stock.service";
import {FormBuilder} from "@angular/forms";
import {Stock} from "../../../dto/GrnResponseDTO";
import {BrandsService} from "../../brands/brands.service";
import {StockDTO} from "../../../dto/StockDTO";

@Component({
    selector: 'app-stock-modal',
    templateUrl: './stock-modal.component.html',
    styleUrls: ['./stock-modal.component.css']
})
export class StockModalComponent implements OnChanges {

    @Input() isVisible: boolean = false;
    @Output() changedVisible: EventEmitter<boolean> = new EventEmitter<boolean>();
    @Output() selectedStock: EventEmitter<Stock> = new EventEmitter<Stock>();
    @ViewChild('stockModal') productsModal: any;

    isLoadingTable: boolean = false;
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

        this.isLoadingTable = true;
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
            },
            complete: () => {
                this.isLoadingTable = false;
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

    showStockModal(modal: any) {
        modal.classList.remove('hidden');
        modal.classList.add('block');
    }

    hideStockModal(modal: any) {
        modal.classList.remove('block');
        modal.classList.add('hidden');
        this.changedVisible.emit(false);
    }

    ngOnChanges(changes: SimpleChanges): void {
        this.isVisible ? this.showStockModal(this.productsModal.nativeElement) : this.hideStockModal(this.productsModal.nativeElement);
    }

    selectStock(stock: Stock) {
        this.selectedStock.emit(stock);
        this.hideStockModal(this.productsModal.nativeElement);
    }

}
