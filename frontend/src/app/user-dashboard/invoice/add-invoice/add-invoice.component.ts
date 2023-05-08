import {Component} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {Stock, StockTableDTO} from "../../../dto/GrnResponseDTO";
import Swal from "sweetalert2";
import {PaymentTypes} from "../../../dto/PaymentTypes";
import {Invoice, InvoiceItem} from "../../../dto/InvoiceDTO";
import {InvoiceService} from "../invoice.service";

@Component({
    selector: 'app-add-invoice',
    templateUrl: './add-invoice.component.html',
    styleUrls: ['./add-invoice.component.css']
})
export class AddInvoiceComponent {

    loading: boolean = false;
    IsVisibleStockModal: boolean = false;
    selectedStock!: Stock;
    stockList: StockTableDTO[] = [];
    grandTotal: number = 0;
    stockForm = this.formBuilder.group({
        stockId: [{value: '', disabled: true}],
        productName: [{value: '', disabled: true}],
        brand: [{value: '', disabled: true}],
        price: [{value: '', disabled: true}],
        expireDate: [{value: '', disabled: true}],
        quantity: ['', Validators.required],
    });
    priceForm = this.formBuilder.group({
        grandTotal: [{value: '', disabled: true}, Validators.required],
        payment: ['', [Validators.required, Validators.min(1)]],
        balance: [{value: '', disabled: true}],
        paymentType: ['', Validators.required],
    })
    protected readonly PaymentTypes = PaymentTypes;
    protected readonly parseInt = parseInt;

    constructor(private formBuilder: FormBuilder, private invoiceService: InvoiceService) {
    }

    showModal(modal: string) {
        switch (modal) {
            case 'stock':
                this.IsVisibleStockModal = true;
                break;
        }
    }

    hideModal(modal: string) {
        switch (modal) {
            case 'stock':
                this.IsVisibleStockModal = false;
                break;
        }
    }

    selectStock(event: Stock) {
        console.log(event);
        this.stockForm.patchValue({
            stockId: event.id
                .toString(),
            productName: event.product.name,
            brand: event.product.brand.name,
            price: event.sellingPrice.toString(),
            expireDate: event.expireDate.toString(),
        });
        this.selectedStock = event;
        this.hideModal('stock');
    }

    addInvoiceItem() {
        if (this.selectedStock) {
            let stockTableDTO: StockTableDTO = {
                id: this.selectedStock.id,
                quantity: parseInt(<string>this.stockForm.get('quantity')?.value),
                sellingPrice: this.selectedStock.sellingPrice,
                manufactureDate: this.selectedStock.manufactureDate,
                expireDate: this.selectedStock.expireDate,
                product: this.selectedStock.product,
                total: this.selectedStock.sellingPrice * parseInt(<string>this.stockForm.get('quantity')?.value)
            }

            let alreadyAddedProduct = this.stockList.find(item => item.id === stockTableDTO.id);
            if (alreadyAddedProduct) {
                let c = confirm("Product already added to the list. Do you want to update the quantity?");
                if (c) {
                    let index = this.stockList.findIndex(item => item.id === stockTableDTO.id);
                    alreadyAddedProduct.quantity += stockTableDTO.quantity;
                    alreadyAddedProduct.sellingPrice = stockTableDTO.sellingPrice;
                    alreadyAddedProduct.total = alreadyAddedProduct.quantity * alreadyAddedProduct.sellingPrice;

                    this.stockList[index] = alreadyAddedProduct;
                    this.calculateGrandTotal();
                    this.clearSelectedStock();
                    Swal.fire({
                        icon: 'success',
                        title: 'Success',
                        text: 'Product updated successfully!',
                    });
                }
                return;
            }

            this.stockList.push(stockTableDTO);
            this.calculateGrandTotal();
            this.clearSelectedStock();
        } else {
            Swal.fire({
                icon: 'warning',
                title: 'Oops...',
                text: 'Please select a product!',
            });
        }
    }

    removeItem(stock: StockTableDTO) {
        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#E7515A',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire(
                    'Deleted!',
                    'Item has been deleted.',
                    'success'
                )
                this.stockList = this.stockList.filter(item => item.id !== stock.id);
                this.calculateGrandTotal();
            }
        })
    }

    clearSelectedStock() {
        this.selectedStock = {} as Stock;
        this.stockForm.reset();
    }

    calculateGrandTotal() {
        let total: number = 0;
        this.stockList.forEach(stock => {
            total += stock.total;
        })
        this.grandTotal = total;

        this.priceForm.controls.grandTotal.setValue(this.grandTotal.toString());
    }

    calculateBalance(payment: number) {
        let balance = payment - this.grandTotal;
        this.priceForm.controls.balance.setValue(balance.toString());
        console.log(this.priceForm.controls.balance.value);
    }

    addInvoice() {
        if (this.stockList.length === 0) {
            Swal.fire({
                icon: 'warning',
                title: 'Oops...',
                text: 'Please add at least one product!',
            });
        } else if (parseInt(<string>this.priceForm.get("payment")?.value) < 0) {
            Swal.fire({
                title: "Warning!",
                text: "Payment cannot be negative",
                icon: "warning"
            })
        }

        const invoiceItems: InvoiceItem[] = [];

        this.stockList.forEach(stock => {
            let invoiceItem: InvoiceItem = {
                stock: {
                    id: stock.id,
                    quantity: stock.quantity,
                    sellingPrice: stock.sellingPrice,
                    manufactureDate: stock.manufactureDate,
                    expireDate: stock.expireDate,
                    product: stock.product,
                },
                total: stock.total,
                quantity: stock.quantity,
                sellingPrice: stock.sellingPrice,
                expireDate: stock.expireDate,
                manufactureDate: stock.manufactureDate
            }
            invoiceItems.push(invoiceItem);
        });

        const invoice: Invoice = {
            invoiceItems: invoiceItems,
            invoicePayment: {
                payment: parseInt(<string>this.priceForm.get("payment")?.value),
                balance: parseInt(<string>this.priceForm.get("balance")?.value),
                paymentType: <PaymentTypes>this.priceForm.get("paymentType")?.value
            }
        }

        this.loading = true;
        this.invoiceService.addInvoice(invoice).subscribe({
            next: (data) => {
                this.clearAll();
                Swal.fire({
                    title: "Invoice Added Successfully!",
                    text: "Do you need to download the Invoice?",
                    icon: "success",
                    showConfirmButton: true,
                    showCancelButton: true,
                    confirmButtonText: "Yes",
                    cancelButtonText: "No",
                }).then((result) => {
                    if (result.isConfirmed) {
                        const blob = new Blob([data], {type: 'application/pdf'});
                        const url = window.URL.createObjectURL(blob);
                        window.open(url);
                    }
                })
            },
            error: (error) => {
                this.loading = false;
                Swal.fire({
                    title: "Error!",
                    text: "Something went wrong",
                    icon: "error"
                })
            },
            complete: () => {
                this.loading = false;
            }
        });

    }

    clearAll() {
        this.stockList = [];
        this.grandTotal = 0;
        this.priceForm.reset();
        this.stockForm.reset();
        this.loading = false;
        this.selectedStock = {} as Stock;
    }
}
