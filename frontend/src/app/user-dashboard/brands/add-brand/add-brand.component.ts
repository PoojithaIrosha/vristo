import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {BrandsService} from "../brands.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-add-brand',
    templateUrl: './add-brand.component.html',
    styleUrls: ['./add-brand.component.css']
})
export class AddBrandComponent implements OnInit {

    categoryList$!: any;

    constructor(private formBuilder: FormBuilder, private brandsService: BrandsService) {
    }

    ngOnInit() {
        this.categoryList$ = this.brandsService.getCategories();
    }

    registerForm = this.formBuilder.group({
        name: ['', Validators.required],
        category: ['', Validators.required],
    })

    registerBrand() {
        if (this.registerForm.valid) {
            if (this.registerForm.controls.name.value && this.registerForm.controls.category.value) {
                var category = {
                    id: 0,
                    name: ''
                };

                this.categoryList$.subscribe((data: any) => {
                    category = data.find((c: any) => c.id == this.registerForm.controls.category.value);
                })

                this.brandsService.createBrand({
                    name: this.registerForm.controls.name.value,
                    category: category
                }).subscribe(
                    {
                        next: data => {
                            Swal.fire({
                                title: 'Success!',
                                text: data.message,
                                icon: 'success',
                            });
                        },
                        error: error => {
                            console.error('There was an error!', error);
                        }
                    }
                )
            }
        }
    }
}
