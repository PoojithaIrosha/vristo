import {Component, OnInit} from '@angular/core';
import {CompanyDTO} from "../../../dto/CompanyDTO";
import {FormBuilder} from "@angular/forms";
import {SuppliersService} from "../suppliers.service";
import {CompanyService} from "./company.service";
import {log10} from "chart.js/helpers";
import _default from "chart.js/dist/plugins/plugin.tooltip";
import numbers = _default.defaults.animations.numbers;

@Component({
    selector: 'app-companies',
    templateUrl: './companies.component.html',
    styleUrls: ['./companies.component.css']
})
export class CompaniesComponent implements OnInit {
    companyList!: CompanyDTO[];
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

    constructor(private formBuilder: FormBuilder, private companyService: CompanyService) {
    }

    ngOnInit() {
        this.searchCompanies(0, this.sizeNum);
    }

    searchCompanies(page: number, size: number) {
        let searchText = '';
        if (this.searchForm.controls.search.value) {
            searchText = this.searchForm.controls.search.value;
        }

        this.companyService.searchCompanies(searchText, page, size).subscribe({
            next: data => {
                this.totalPages = [];
                for (let i = 1; i <= data.totalPages; i++) {
                    this.totalPages[i - 1] = i;
                }
                this.currentPage = data.pageable.pageNumber;
                this.totalElements = data.totalElements;
                this.last = data.last;
                this.offset = data.pageable.offset;
                this.companyList = data.content;
            },
            error: err => console.log(err.error)
        });
    }

    paginate(page: number) {
        this.searchCompanies(page, this.sizeNum);
    }

    sizeChange(size: string) {
        this.sizeNum = parseInt(size);
        this.paginate(this.currentPage);
    }

}

