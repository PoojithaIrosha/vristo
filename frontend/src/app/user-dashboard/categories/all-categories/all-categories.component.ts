import {Component, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {CategoriesService} from "../categories.service";

@Component({
    selector: 'app-all-categories',
    templateUrl: './all-categories.component.html',
    styleUrls: ['./all-categories.component.css']
})
export class AllCategoriesComponent implements OnInit {
    totalPages: number[] = [];
    totalElements!: number;
    currentPage: number = 0;
    sizesList: number[] = [10, 20, 30, 40, 50]
    sizeNum: number = this.sizesList[0];
    last: boolean = false;
    offset!: number;
    categoryList!: { id: number, name: string }[];

    searchForm = this.formBuilder.group({
        search: ['']
    });

    constructor(private formBuilder: FormBuilder, private categoriesService: CategoriesService) {
    }

    ngOnInit() {
        this.searchCategories(0, this.sizeNum);
    }

    searchCategories(page: number, size: number) {
        let searchText = '';
        if (this.searchForm.controls.search.value) {
            searchText = this.searchForm.controls.search.value;
        }

        this.categoriesService.searchCategories(page, size, searchText).subscribe({
            next: data => {
                this.totalPages = [];
                for (let i = 1; i <= data.totalPages; i++) {
                    this.totalPages[i - 1] = i;
                }
                this.currentPage = data.pageable.pageNumber;
                this.totalElements = data.totalElements;
                this.last = data.last;
                this.offset = data.pageable.offset;
                this.categoryList = data.content;
            },
            error: err => {
                console.log(err);
            }
        });
    }

    paginate(page: number) {
        this.searchCategories(page, this.sizeNum);
    }

    sizeChange(size: string) {
        this.sizeNum = parseInt(size);
        this.paginate(this.currentPage);
    }

}
