import {Component, OnInit} from '@angular/core';
import {SupplierDTO} from "../../../dto/SupplierDTO";
import {FormBuilder} from "@angular/forms";
import {SuppliersService} from "../suppliers.service";

@Component({
    selector: 'app-all-suppliers',
    templateUrl: './all-suppliers.component.html',
    styleUrls: ['./all-suppliers.component.css']
})
export class AllSuppliersComponent implements OnInit {

    supplierList!: {
        id: number;
        name: string;
        email: string;
        mobile: string;
        company: {
            id: number;
            name: string;
            email: string;
            mobile: string;
            address: string;
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

    constructor(private formBuilder: FormBuilder, private suppliersService: SuppliersService) {
    }

    ngOnInit(): void {
        this.searchSuppliers(0, this.sizeNum);
    }

    searchSuppliers(page: number, size: number) {
        let searchText = '';
        if (this.searchForm.controls.search.value) {
            searchText = this.searchForm.controls.search.value;
        }

        this.suppliersService.searchSuppliers(searchText, page, size).subscribe({
            next: data => {
                this.totalPages = [];
                for (let i = 1; i <= data.totalPages; i++) {
                    this.totalPages[i - 1] = i;
                }
                this.currentPage = data.pageable.pageNumber;
                this.totalElements = data.totalElements;
                this.last = data.last;
                this.offset = data.pageable.offset;
                this.supplierList = data.content;
            },
            error: err => console.log(err.error)
        });
    }

    paginate(page: number) {
        this.searchSuppliers(page, this.sizeNum);
    }

    sizeChange(size: string) {
        this.sizeNum = parseInt(size);
        this.paginate(this.currentPage);
    }

}
