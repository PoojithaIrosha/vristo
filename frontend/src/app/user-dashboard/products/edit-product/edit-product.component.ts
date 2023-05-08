import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {ProductsService} from "../products.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-edit-product',
    templateUrl: './edit-product.component.html',
    styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {

    brandList$!: any;
    unitList$!: any;

    updateForm = this.formBuilder.group({
        id: [{value: 0, disabled: true}],
        name: ["", Validators.required],
        brand: ["", Validators.required],
        unit: ["", Validators.required],
    })

    constructor(private formBuilder: FormBuilder, private route: ActivatedRoute, private productsService: ProductsService, private router: Router) {

    }

    ngOnInit() {

        let productId = this.route.snapshot.paramMap.get("productId");

        if (productId) {
            this.productsService.getProduct(Number.parseInt(productId)).subscribe({
                next: data => {
                    this.updateForm.controls.id.setValue(data.id);
                    this.updateForm.controls.name.setValue(data.name);
                    this.updateForm.controls.brand.setValue(data.brand.id);
                    this.updateForm.controls.unit.setValue(data.unit.id);
                },
                error: err => {
                    Swal.fire({
                        title: 'Error!',
                        text: err.error.detail,
                        icon: 'error',
                    }).then((rs) => {
                        if (rs.isConfirmed) {
                            this.router.navigate(["/products"]);
                        }
                    });
                }
            });
        }

        this.brandList$ = this.productsService.getBrands();
        this.unitList$ = this.productsService.getUnits();
    }


    updateProduct() {
        if (this.updateForm.valid) {
            if (this.updateForm.controls.id.value && this.updateForm.controls.name.value && this.updateForm.controls.brand.value && this.updateForm.controls.unit.value) {
                this.productsService.updateProduct({
                    id: this.updateForm.controls.id.value,
                    name: this.updateForm.controls.name.value,
                    brand: Number.parseInt(this.updateForm.controls.brand.value),
                    unit: Number.parseInt(this.updateForm.controls.unit.value)
                }).subscribe({
                    next: data => {
                        Swal.fire({
                            title: 'Success!',
                            text: data.message,
                            icon: 'success',
                        }).then((rs) => {
                            if (rs.isConfirmed) {
                                this.router.navigate(["/products"]);
                            }
                        });
                    },
                    error: err => {
                        Swal.fire({
                            title: 'Error!',
                            text: err.error.detail,
                            icon: 'error',
                        });
                    }
                });
            }
        }
    }
}
