import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {BrandsService} from "../brands.service";
import {ActivatedRoute, Router} from "@angular/router";
import Swal from "sweetalert2";
import {Observable} from "rxjs";

@Component({
    selector: 'app-edit-brand',
    templateUrl: './edit-brand.component.html',
    styleUrls: ['./edit-brand.component.css']
})
export class EditBrandComponent implements OnInit {

    categoryList$!: Observable<any>;

    updateForm = this.formBuilder.group({
        id: [{value: '', disabled: true}, Validators.required],
        name: ['', Validators.required],
        category: ['', Validators.required],
    });

    constructor(private formBuilder: FormBuilder, private brandService: BrandsService, private route: ActivatedRoute, private router: Router) {
    }

    ngOnInit() {
        this.categoryList$ = this.brandService.getCategories();

        var brandId = this.route.snapshot.paramMap.get("brandId");
        if (brandId) {
            this.brandService.getBrand(parseInt(brandId)).subscribe({
                next: (data) => {
                    this.updateForm.patchValue({
                        id: data.id,
                        name: data.name,
                        category: data.category.id
                    });
                },
                error: err => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'Something went wrong!',
                    }).then((rs) => {
                        if (rs.isConfirmed) {
                            this.router.navigate(['/brands']);
                        }
                    })
                }
            });
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Oops...',
                text: 'Something went wrong!',
            }).then((rs) => {
                if (rs.isConfirmed) {
                    this.router.navigate(['/brands']);
                }
            })
        }
    }

    updateBrand() {
        if (this.updateForm.valid) {
            if (this.updateForm.controls.name.value && this.updateForm.controls.id.value && this.updateForm.controls.category.value) {

                var category = {id: 0, name: ''};

                this.categoryList$.subscribe(data => {
                    category = data.find((c: any) => c.id.toString() == this.updateForm.controls.category.value);
                });

                this.brandService.updateBrand({
                    id: parseInt(this.updateForm.controls.id.value),
                    name: this.updateForm.controls.name.value,
                    category: category
                }).subscribe({
                    next: (data) => {
                        Swal.fire({
                            icon: 'success',
                            title: 'Success',
                            text: 'Brand updated successfully',
                        }).then((rs) => {
                            if (rs.isConfirmed) {
                                this.router.navigate(['/brands']);
                            }
                        })
                    },
                    error: err => {
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: 'Something went wrong!',
                        }).then((rs) => {
                            if (rs.isConfirmed) {
                                this.router.navigate(['/brands']);
                            }
                        })
                    }
                });
            }
        }
    }

}
