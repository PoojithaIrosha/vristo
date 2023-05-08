import {Component} from '@angular/core';
import {Supplier} from "../../../dto/Supplier";
import {FormBuilder, Validators} from "@angular/forms";
import {Product} from "../../../dto/Product";
import {GrnItemDTO} from "../../../dto/GrnItemDTO";
import Swal from "sweetalert2";
import {PaymentTypes} from "../../../dto/PaymentTypes";
import {GrnService} from "../grn.service";
import {Grn, GrnItem} from "../../../dto/GrnDTO";

@Component({
    selector: 'app-add-grn',
    templateUrl: './add-grn.component.html',
    styleUrls: ['./add-grn.component.css']
})
export class AddGrnComponent {
    loading: boolean = false;
    IsVisibleSupplierModal: boolean = false;
    IsVisibleProductModal: boolean = false;
    grandTotal: number = 0;
    productList: GrnItemDTO[] = [];
    selectedProduct!: Product;
    selectedSupplier!: Supplier;

    supplierForm = this.formBuilder.group({
        supplierId: [{value: '', disabled: true}],
        supplierName: [{value: '', disabled: true}],
    })

    productForm = this.formBuilder.group({
        productId: [{value: '', disabled: true}],
        productName: [{value: '', disabled: true}],
    })

    stockForm = this.formBuilder.group({
        quantity: ['', [Validators.required, Validators.min(1)]],
        buyingPrice: ['', [Validators.required, Validators.min(1)]],
        sellingPrice: ['', [Validators.required, Validators.min(1)]],
        manufactureDate: ['', Validators.required],
        expiryDate: ['', Validators.required],
    })

    priceForm = this.formBuilder.group({
        grandTotal: [{value: '', disabled: true}, Validators.required],
        payment: ['', [Validators.required, Validators.min(1)]],
        balance: [{value: '', disabled: true}],
        paymentType: ['', Validators.required],
    })
    protected readonly PaymentTypes = PaymentTypes;
    protected readonly parseInt = parseInt;

    constructor(private formBuilder: FormBuilder, private grnService: GrnService) {
        this.priceForm.controls.grandTotal.setValue("0");
    }

    showModal(modal: string) {
        switch (modal) {
            case 'supplier':
                this.IsVisibleSupplierModal = true;
                break;
            case 'product':
                this.IsVisibleProductModal = true;
                break;
        }
    }

    hideModal(modal: string) {
        switch (modal) {
            case 'supplier':
                this.IsVisibleSupplierModal = false;
                break;
            case 'product':
                this.IsVisibleProductModal = false;
                break;
        }
    }

    selectSupplier(event: Supplier) {
        this.supplierForm.patchValue({
            supplierId: event.id.toString(),
            supplierName: event.name,
        });
        this.selectedSupplier = event;
        this.hideModal('supplier');
    }

    selectProduct(event: Product) {
        this.productForm.patchValue({
            productId: event.id.toString(),
            productName: event.name,
        });
        this.selectedProduct = event;
        this.hideModal('product');
    }

    addGrnItem() {

        if (this.selectedProduct) {
            let productId = this.selectedProduct.id;
            let quantity = parseInt(<string>this.stockForm.get('quantity')?.value);
            let buyingPrice = parseInt(<string>this.stockForm.get('buyingPrice')?.value);
            let sellingPrice = parseInt(<string>this.stockForm.get('sellingPrice')?.value);
            let manufactureDate = <string>this.stockForm.get('manufactureDate')?.value;
            let expireDate = <string>this.stockForm.get('expiryDate')?.value;
            let total = (quantity ?? 0) * (buyingPrice ?? 0);

            let today = new Date();

            if (new Date(manufactureDate) > today) {
                Swal.fire({
                    icon: 'warning',
                    title: 'Oops...',
                    text: 'Manufacture date cannot be a future date!',
                })
                return;
            }

            if (new Date(expireDate) <= today) {
                Swal.fire({
                    icon: 'warning',
                    title: 'Oops...',
                    text: 'Expire date cannot be a past date!',
                })
                return;
            }


            let alreadyAddedProduct = this.productList.find(item => item.product.id === productId);
            if (alreadyAddedProduct !== undefined) {
                let c = confirm("Product already added to the list. Do you want to update the quantity?");

                if (c) {
                    let index = this.productList.findIndex(item => item.product.id === productId);
                    alreadyAddedProduct.quantity = alreadyAddedProduct.quantity += quantity;
                    alreadyAddedProduct.buyingPrice = buyingPrice;
                    alreadyAddedProduct.sellingPrice = sellingPrice;
                    alreadyAddedProduct.total = alreadyAddedProduct.buyingPrice * alreadyAddedProduct.quantity;
                    alreadyAddedProduct.expireDate = new Date(expireDate);
                    alreadyAddedProduct.manufactureDate = new Date(manufactureDate);

                    this.productList[index] = alreadyAddedProduct;
                    this.calculateGrandTotal();
                    this.clearAfterItemAdded();
                    Swal.fire({
                        icon: 'success',
                        title: 'Success',
                        text: 'Product updated successfully!',
                    })
                }
                return;
            }

            let newGrnItem: GrnItemDTO = {
                product: this.selectedProduct,
                quantity: quantity,
                buyingPrice: buyingPrice,
                sellingPrice: sellingPrice,
                manufactureDate: new Date(manufactureDate),
                expireDate: new Date(expireDate),
                total: total
            }

            this.productList.push(newGrnItem);
            this.calculateGrandTotal();
            this.clearAfterItemAdded();
        } else {
            Swal.fire({
                icon: 'warning',
                title: 'Oops...',
                text: 'Please select a product!',
            })
        }
    }

    removeGrnItem(product: GrnItemDTO) {
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
                this.productList = this.productList.filter(item => item.product.id !== product.product.id);
                this.calculateGrandTotal();
            }
        })
    }

    calculateGrandTotal() {
        let total: number = 0;
        this.productList.forEach(product => {
            total += product.total;
        })
        this.grandTotal = total;

        this.priceForm.controls.grandTotal.setValue(this.grandTotal.toString());
    }

    calculateBalance(payment: number) {
        let balance = payment - this.grandTotal;
        this.priceForm.controls.balance.setValue(balance.toString());
        console.log(this.priceForm.controls.balance.value);
    }

    addGrn() {
        if (this.selectedSupplier == null) {
            Swal.fire({
                title: "Warning!",
                text: "Please select a supplier first.",
                icon: "warning"
            })
        } else if (this.productList.length <= 0) {
            Swal.fire({
                title: "Warning!",
                text: "Please add products to the grn before checkout.",
                icon: "warning"
            })
        } else if (parseInt(<string>this.priceForm.get("payment")?.value) < 0) {
            Swal.fire({
                title: "Warning!",
                text: "Payment cannot be negative",
                icon: "warning"
            })
        } else {
            const grnItems: GrnItem[] = [];

            this.productList.forEach(item => {
                let grnItem: GrnItem = {
                    product: item.product,
                    quantity: item.quantity,
                    buyingPrice: item.buyingPrice,
                    sellingPrice: item.sellingPrice,
                    manufactureDate: item.manufactureDate,
                    expireDate: item.expireDate,
                }
                grnItems.push(grnItem);
            })

            const grn: Grn = {
                supplier: this.selectedSupplier,
                grnPayment: {
                    payment: parseInt(<string>this.priceForm.get('payment')?.value),
                    balance: parseInt(<string>this.priceForm.get('balance')?.value),
                    paymentType: <PaymentTypes>this.priceForm.get('paymentType')?.value
                },
                grnItems: grnItems
            };

            this.loading = true;
            this.grnService.addGrn(grn).subscribe({
                next: data => {
                    this.clearAll();
                    Swal.fire({
                        title: "GRN Added Successfully!",
                        text: "Do you need to download the GRN?",
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
                error: err => {
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
    }

    clearAfterItemAdded() {
        this.selectedProduct = {} as Product;
        this.productForm.reset();
        this.stockForm.reset();
    }

    clearAll() {
        this.productList = [];
        this.selectedProduct = {} as Product;
        this.selectedSupplier = {} as Supplier;
        this.grandTotal = 0;
        this.supplierForm.reset();
        this.productForm.reset();
        this.priceForm.reset()
        this.loading = false;
    }

}
