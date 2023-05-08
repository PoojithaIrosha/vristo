import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {CategoriesService} from "../categories.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-edit-category',
    templateUrl: './edit-category.component.html',
    styleUrls: ['./edit-category.component.css']
})
export class EditCategoryComponent implements OnInit {

    editCategoryForm = this.formBuilder.group({
        id: [{value: '', disabled: true}],
        name: ['', Validators.required],
    });

    constructor(private formBuilder: FormBuilder, private route: ActivatedRoute, private router: Router, private categoriesService: CategoriesService) {
    }

    ngOnInit() {
        let categoryId = this.route.snapshot.paramMap.get("categoryId");
        if (categoryId) {
            this.categoriesService.getCategoryById(parseInt(categoryId)).subscribe({
                next: (category) => {
                    this.editCategoryForm.patchValue({
                        id: category.id,
                        name: category.name,
                    });
                }
            });
        }
    }

    updateCategory() {
        if (this.editCategoryForm.valid) {
            if (this.editCategoryForm.controls.id.value && this.editCategoryForm.controls.name.value) {
                this.categoriesService.editCategory({
                    id: parseInt(this.editCategoryForm.controls.id.value),
                    name: this.editCategoryForm.controls.name.value,
                }).subscribe({
                    next: value => {
                        Swal.fire({
                            title: 'Success!',
                            text: value.message,
                            icon: 'success',
                        }).then((rs) => {
                            if (rs.isConfirmed) {
                                this.router.navigate(['/categories']);
                            }
                        });
                    },
                    error: err => {
                        console.log(err)
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
