import {Component, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {BrandsService} from "../brands.service";

@Component({
    selector: 'app-all-brands',
    templateUrl: './all-brands.component.html',
    styleUrls: ['./all-brands.component.css']
})
export class AllBrandsComponent implements OnInit {
    brandList!: {
        id: number;
        name: string;
        category: {
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

    constructor(private formBuilder: FormBuilder, private brandsService: BrandsService) {
    }

    ngOnInit() {
        this.searchBrands(0, this.sizeNum);
    }

    searchBrands(page: number, size: number) {
        let searchText = '';
        if (this.searchForm.controls.search.value) {
            searchText = this.searchForm.controls.search.value;
        }

        this.brandsService.searchBrands(page, size, searchText).subscribe({
            next: data => {
                this.totalPages = [];
                for (let i = 1; i <= data.totalPages; i++) {
                    this.totalPages[i - 1] = i;
                }
                this.currentPage = data.pageable.pageNumber;
                this.totalElements = data.totalElements;
                this.last = data.last;
                this.offset = data.pageable.offset;
                this.brandList = data.content;
            },
            error: err => {
                console.log(err);
            }
        });
    }

    paginate(page: number) {
        this.searchBrands(page, this.sizeNum);
    }

    sizeChange(size: string) {
        this.sizeNum = parseInt(size);
        this.paginate(this.currentPage);
    }
}
