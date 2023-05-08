import {Component} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import Swal from "sweetalert2";
import {InvoiceService} from "../invoice.service";
import {InvoiceResponse} from "../../../dto/InvoiceResponseDTO";

@Component({
  selector: 'app-invoice-history',
  templateUrl: './invoice-history.component.html',
  styleUrls: ['./invoice-history.component.css']
})
export class InvoiceHistoryComponent {
  generatingGrnId: number | null = null;
  invoiceList!: InvoiceResponse[];
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

  constructor(private invoiceService: InvoiceService, private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.searchInvoices(0, this.sizeNum);
  }

  searchInvoices(page: number, size: number) {
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

    this.invoiceService.searchInvoices(data).subscribe({
      next: data => {
        this.totalPages = [];
        for (let i = 1; i <= data.totalPages; i++) {
          this.totalPages[i - 1] = i;
        }
        this.currentPage = data.pageable.pageNumber;
        this.totalElements = data.totalElements;
        this.last = data.last;
        this.offset = data.pageable.offset;
        this.invoiceList = data.content;
      },
      error: err => {
        console.log(err.error)
      }
    })
  }

  paginate(page: number) {
    this.searchInvoices(page, this.sizeNum);
  }

  sizeChange(size: string) {
    this.sizeNum = parseInt(size);
    this.paginate(this.currentPage);
  }

  generateReport(grnId: number) {
    this.generatingGrnId = grnId;
    this.invoiceService.generateReport(grnId).subscribe({
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
