import {Component} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {CategoriesService} from "../categories.service";
import Swal from "sweetalert2";
import {Router} from "@angular/router";

@Component({
    selector: 'app-add-category',
    templateUrl: './add-category.component.html',
    styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent {

    constructor(private formBuilder: FormBuilder, private categoriesService: CategoriesService, private router: Router) {
    }

    registerForm = this.formBuilder.group({
        name: [''],
    });

    registerCategory() {
        if (this.registerForm.valid && this.registerForm.value.name) {
            this.categoriesService.registerCategory({
                name: this.registerForm.value.name
            }).subscribe({
                next: (response) => {
                    console.log(response);
                    Swal.fire({
                        title: 'Success!',
                        text: response.message,
                        icon: 'success',
                    }).then((rs) => {
                        if (rs.isConfirmed) {
                            this.router.navigate(['/categories'])
                        }
                    })
                },
                error: err => {
                    Swal.fire({
                        title: 'Error!',
                        text: err.error.detail,
                        icon: 'error',
                    })
                }
            });
        }
    }
}
