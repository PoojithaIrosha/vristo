import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {SuppliersService} from "../../suppliers/suppliers.service";
import {Supplier} from "../../../dto/Supplier";

@Component({
    selector: 'app-supplier-modal',
    templateUrl: './supplier-modal.component.html',
    styleUrls: ['./supplier-modal.component.css']
})
export class SupplierModalComponent implements OnInit, OnChanges {

    @Input() isVisible: boolean = false;
    @Output() changedVisible: EventEmitter<boolean> = new EventEmitter<boolean>();
    @Output() selectedSupplier: EventEmitter<Supplier> = new EventEmitter<Supplier>();

    @ViewChild('supplierModal') supplierModal: any;

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

    showSupplierModal(modal: any) {
        modal.classList.remove('hidden');
        modal.classList.add('block');
    }

    hideSupplierModal(modal: any) {
        modal.classList.remove('block');
        modal.classList.add('hidden');
        this.changedVisible.emit(false);
    }

    ngOnChanges(changes: SimpleChanges): void {
        this.isVisible ? this.showSupplierModal(this.supplierModal.nativeElement) : this.hideSupplierModal(this.supplierModal.nativeElement);
    }

    selectSupplier(supplier: Supplier) {
        this.selectedSupplier.emit(supplier);
    }

}
