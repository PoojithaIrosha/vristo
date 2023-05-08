import {Component, OnInit} from '@angular/core';
import {GrnService} from "../grn.service";
import {GrnResponse} from "../../../dto/GrnResponseDTO";
import {FormBuilder} from "@angular/forms";
import Swal from "sweetalert2";
import {HttpClient} from "@angular/common/http";

@Component({
    selector: 'app-grn-history',
    templateUrl: './grn-history.component.html',
    styleUrls: ['./grn-history.component.css']
})
export class GrnHistoryComponent implements OnInit {
    generatingGrnId: number | null = null;
    grnList!: GrnResponse[];
    totalPages: number[] = [];
    totalElements!: number;
    currentPage: number = 0;
    sizesList: number[] = [10, 20, 30, 40, 50]
    sizeNum: number = this.sizesList[0];
    last: boolean = false;
    offset!: number;
    searchForm = this.formBuilder.group({
        search: [''],
        startDate: [''],
        endDate: [''],
    });

    constructor(private grnService: GrnService, private formBuilder: FormBuilder, private http: HttpClient) {
    }

    ngOnInit() {
        this.searchGrns(0, this.sizeNum);
    }

    searchGrns(page: number, size: number) {
        let startDate!: Date;
        let endDate!: Date;

        if (this.searchForm.controls.startDate.value) {
            startDate = new Date(this.searchForm.controls.startDate.value);
        }

        if (this.searchForm.controls.endDate.value) {
            endDate = new Date(this.searchForm.controls.endDate.value);
        }


        let data = {
            page: page,
            size: size,
            startDate: startDate,
            endDate: endDate
        };

        this.grnService.searchGrns(data).subscribe({
            next: data => {
                this.totalPages = [];
                for (let i = 1; i <= data.totalPages; i++) {
                    this.totalPages[i - 1] = i;
                }
                this.currentPage = data.pageable.pageNumber;
                this.totalElements = data.totalElements;
                this.last = data.last;
                this.offset = data.pageable.offset;
                this.grnList = data.content;
            },
            error: err => {
                console.log(err.error)
            }
        })
    }

    paginate(page: number) {
        this.searchGrns(page, this.sizeNum);
    }

    sizeChange(size: string) {
        this.sizeNum = parseInt(size);
        this.paginate(this.currentPage);
    }

    generateReport(grnId: number) {
        this.generatingGrnId = grnId;
        this.grnService.generateReport(grnId).subscribe({
            next: data => {
                const blob = new Blob([data], {type: 'application/pdf'});
                const url = window.URL.createObjectURL(blob);
                window.open(url);
            },
            error: err => {
                Swal.fire({
                    title: 'Error!',
                    text: err.error.detail,
                    icon: 'error',
                })
            },
            complete: () => {
                this.generatingGrnId = null;
            }
        })
    }
}
