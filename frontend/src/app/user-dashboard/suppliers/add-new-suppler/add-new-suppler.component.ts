import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {SuppliersService} from "../suppliers.service";
import {CompanyDTO} from "../../../dto/CompanyDTO";
import Swal from "sweetalert2";
import _default from "chart.js/dist/plugins/plugin.tooltip";
import {Observable} from "rxjs";

@Component({
    selector: 'app-add-new-suppler',
    templateUrl: './add-new-suppler.component.html',
    styleUrls: ['./add-new-suppler.component.css']
})
export class AddNewSupplerComponent implements OnInit {

    companies!: CompanyDTO[];
    companies$!: Observable<CompanyDTO[]>;

    ngOnInit() {
        this.companies$ = this.supplierService.getAllCompanies();
    }

    constructor(private formBuilder: FormBuilder, private supplierService: SuppliersService) {
    }

    registerSupplierForm = this.formBuilder.group({
        name: ['', [Validators.required, Validators.minLength(3)]],
        email: ['', [Validators.required, Validators.minLength(3), Validators.email]],
        mobile: ['', [Validators.required, Validators.minLength(3)]],
        company: ['', [Validators.required]]
    });

    registerSupplier() {
        if (this.registerSupplierForm.valid) {
            if (this.registerSupplierForm.controls.name.value && this.registerSupplierForm.controls.email.value && this.registerSupplierForm.controls.mobile.value && this.registerSupplierForm.controls.company.value) {
                this.supplierService.registerSupplier({
                    name: this.registerSupplierForm.controls.name.value,
                    email: this.registerSupplierForm.controls.email.value,
                    mobile: this.registerSupplierForm.controls.mobile.value,
                    company: Number.parseInt(this.registerSupplierForm.controls.company.value)
                }).subscribe({
                    next: (response) => {
                        Swal.fire({
                            title: 'Success!',
                            text: response.message,
                            icon: 'success',
                        }).then((result) => {
                            if (result.isConfirmed) {
                                this.registerSupplierForm.reset();
                            }
                        });
                    },
                    error: (err) => {
                        Swal.fire({
                            title: 'Error!',
                            text: "Something went wrong!",
                            icon: 'error',
                        })
                    }
                });
            }
        }

    }

}
