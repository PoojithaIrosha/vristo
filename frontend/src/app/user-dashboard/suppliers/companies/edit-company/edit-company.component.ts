import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {CompanyService} from "../company.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-edit-company',
    templateUrl: './edit-company.component.html',
    styleUrls: ['./edit-company.component.css']
})
export class EditCompanyComponent implements OnInit {

    constructor(private formBuilder: FormBuilder, private route: ActivatedRoute, private companyService: CompanyService, private router: Router) {
    }

    companyForm = this.formBuilder.group({
        id: [{value: 0, disabled: true}],
        name: ['', [Validators.required]],
        email: ['', [Validators.required, Validators.email]],
        mobile: ['', Validators.required],
        address: ['', Validators.required],
    })

    ngOnInit() {
        let companyId = this.route.snapshot.paramMap.get("companyId")
        if (companyId) {
            this.companyService.getCompany(companyId).subscribe({
                next: (company) => {
                    this.companyForm.patchValue(company);
                },
                error: (err) => {
                    Swal.fire({
                        title: 'Error!',
                        text: err.error.detail,
                        icon: 'error',
                    }).then((result) => {
                        this.router.navigate(['/suppliers/companies'])
                    });
                }
            });
        }
    }

    updateCompany() {
        if (this.companyForm.valid) {
            if (this.companyForm.controls.email.value && this.companyForm.controls.name.value && this.companyForm.controls.address.value && this.companyForm.controls.mobile.value && this.companyForm.controls.id.value) {
                this.companyService.updateCompany({
                    id: this.companyForm.controls.id.value,
                    name: this.companyForm.controls.name.value,
                    email: this.companyForm.controls.email.value,
                    mobile: this.companyForm.controls.mobile.value,
                    address: this.companyForm.controls.address.value,
                }).subscribe({
                    next: (response) => {
                        Swal.fire({
                            title: 'Success!',
                            text: "Company updated successfully",
                            icon: 'success',
                        }).then((result) => {
                            this.router.navigate(['/suppliers/companies'])
                        });
                    },
                    error: (err) => {
                        Swal.fire({
                            title: 'Error!',
                            text: "Error while updating company",
                            icon: 'error',
                        });
                    }
                });
            }
        }
    }
}
