import {Component} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {CompanyService} from "../company.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-add-new-company',
    templateUrl: './add-new-company.component.html',
    styleUrls: ['./add-new-company.component.css']
})
export class AddNewCompanyComponent {

    constructor(private formBuilder: FormBuilder, private companyService: CompanyService) {
    }

    registerCompanyForm = this.formBuilder.group({
        name: ['', [Validators.required]],
        email: ['', [Validators.required, Validators.email]],
        mobile: ['', Validators.required],
        address: ['', Validators.required],
    })

    registerCompany() {
        if (this.registerCompanyForm.valid) {
            if (this.registerCompanyForm.controls.email.value && this.registerCompanyForm.controls.name.value && this.registerCompanyForm.controls.mobile.value && this.registerCompanyForm.controls.address.value) {
                this.companyService.registerCompany({
                    name: this.registerCompanyForm.controls.name.value,
                    email: this.registerCompanyForm.controls.email.value,
                    mobile: this.registerCompanyForm.controls.mobile.value,
                    address: this.registerCompanyForm.controls.address.value
                }).subscribe({
                    next: (response) => {
                        Swal.fire({
                            title: 'Success',
                            text: "Company registered successfully.",
                            icon: 'success',
                        }).then((result) => {
                            if (result.isConfirmed) {
                                this.registerCompanyForm.reset();
                            }
                        });
                    },
                    error: (err) => {
                        console.log(err)
                        Swal.fire({
                            title: 'Error',
                            text: err.error.detail,
                            icon: 'error',
                        })
                    }
                });
            }
        }
    }
}
