import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {ProductsService} from "../products.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-add-new-product',
    templateUrl: './add-new-product.component.html',
    styleUrls: ['./add-new-product.component.css']
})
export class AddNewProductComponent implements OnInit {

    brandList$!: any;
    unitList$!: any;

    registerForm = this.formBuilder.group({
        name: ["", Validators.required],
        brand: ["", Validators.required],
        unit: ["", Validators.required],
    });

    constructor(private formBuilder: FormBuilder, private productsService: ProductsService) {
    }

    ngOnInit() {
        this.brandList$ = this.productsService.getBrands();

        this.unitList$ = this.productsService.getUnits();
    }

    registerProduct() {
        if (this.registerForm.valid) {
            if (this.registerForm.controls.name.value && this.registerForm.controls.brand.value && this.registerForm.controls.unit.value) {
                this.productsService.registerProduct({
                    name: this.registerForm.controls.name.value,
                    brand: Number.parseInt(this.registerForm.controls.brand.value),
                    unit: Number.parseInt(this.registerForm.controls.unit.value)
                }).subscribe({
                    next: data => {
                        Swal.fire({
                            title: "Success!",
                            icon: "success",
                            text: data.message,
                        }).then(rs => {
                            if (rs.isConfirmed) {
                                this.registerForm.reset();
                            }
                        })
                    },
                    error: err => {
                        Swal.fire({
                            title: "Error",
                            icon: "error",
                            text: err.error.detail,
                        })
                    }
                })

            }

        } else {
            Swal.fire({
                title: "Error!",
                icon: "error",
                text: "Please fill all the fields!"
            })
        }
    }
}

