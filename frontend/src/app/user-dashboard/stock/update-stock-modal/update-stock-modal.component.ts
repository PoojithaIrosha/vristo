import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {Stock} from "../../../dto/GrnResponseDTO";
import {StockService} from "../stock.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-update-stock-modal',
    templateUrl: './update-stock-modal.component.html',
    styleUrls: ['./update-stock-modal.component.css']
})
export class UpdateStockModalComponent implements OnChanges {

    @Input() isVisible: boolean = false;
    @Input() stock!: Stock;
    @Output() changedVisible: EventEmitter<boolean> = new EventEmitter<boolean>();
    @ViewChild('stockUpdateModal') modal: any;

    constructor(private formBuilder: FormBuilder, private stockService: StockService) {
    }

    updateForm = this.formBuilder.group({
        stockId: [{value: '', disabled: true}],
        currentPrice: [{value: '', disabled: true}],
        newPrice: ['', [Validators.required, Validators.min(1)]]
    });

    showModal(modal: any) {
        this.updateForm.get('stockId')?.setValue(this.stock.id.toString());
        this.updateForm.get('currentPrice')?.setValue(this.stock.sellingPrice.toString());
        modal.classList.remove('hidden');
        modal.classList.add('block');
    }

    hideModal(modal: any) {
        modal.classList.remove('block');
        modal.classList.add('hidden');
        this.changedVisible.emit(false);
    }

    ngOnChanges(changes: SimpleChanges): void {
        this.isVisible ? this.showModal(this.modal.nativeElement) : this.hideModal(this.modal.nativeElement);
    }

    updatePrice() {
        let stockId = <string>this.updateForm.get('stockId')?.value;
        let newPrice = <string>this.updateForm.get('newPrice')?.value;

        this.stockService.updateStockPrice({
            stockId: parseInt(stockId),
            newPrice: parseInt(newPrice)
        }).subscribe({
            next: (data) => {
                Swal.fire({
                    title: 'Success!',
                    text: 'Stock price updated successfully!',
                    icon: 'success',
                }).then((res) => {
                    if (res.isConfirmed) {
                        this.hideModal(this.modal.nativeElement);
                        this.changedVisible.emit(false);
                    }
                })

            },
            error: (err) => {
                Swal.fire({
                    title: 'Error!',
                    text: 'Something went wrong!',
                    icon: 'error',
                }).then((res) => {
                    if (res.isConfirmed) {
                        this.hideModal(this.modal.nativeElement);
                        this.changedVisible.emit(false);
                    }
                })
            }
        })
    }
}
