import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {SuppliersService} from "../suppliers.service";
import Swal from "sweetalert2";
import {FormBuilder, Validators} from "@angular/forms";
import {CompanyDTO} from "../../../dto/CompanyDTO";
import {Observable} from "rxjs";

@Component({
    selector: 'app-edit-supplier',
    templateUrl: './edit-supplier.component.html',
    styleUrls: ['./edit-supplier.component.css']
})
export class EditSupplierComponent implements OnInit {
    companies$!: Observable<CompanyDTO[]>;

    SupplierForm = this.formBuilder.group({
        id: [{value: 0, disabled: true}, [Validators.required, Validators.minLength(3)]],
        name: ['', [Validators.required, Validators.minLength(3)]],
        email: ['', [Validators.required, Validators.minLength(3), Validators.email]],
        mobile: ['', [Validators.required, Validators.minLength(3)]],
        company: [0, [Validators.required]]
    });

    constructor(private route: ActivatedRoute, private suppliersService: SuppliersService, private formBuilder: FormBuilder, private router: Router) {
    }

    ngOnInit() {
        let supplierId = this.route.snapshot.paramMap.get('supplierId');
        if (supplierId != null) {
            this.suppliersService.getSupplierById(supplierId).subscribe({
                next: (supplier) => {
                    this.SupplierForm.patchValue({
                        id: supplier.id,
                        name: supplier.name,
                        email: supplier.email,
                        mobile: supplier.mobile,
                        company: supplier.company.id
                    });
                },
                error: (err) => {
                    Swal.fire({
                        title: 'Error!',
                        text: err.error.detail,
                        icon: 'error',
                    }).then((result) => {
                        if (result.isConfirmed) {
                            this.router.navigate(['/suppliers']);
                        }
                    });
                }
            });
        }

        this.companies$ = this.suppliersService.getAllCompanies();
    }

    updateSupplier() {
        let selectedCompany!: any;

        if (this.SupplierForm.controls.company.value && typeof this.SupplierForm.controls.company.value === "number") {
            let companyId = this.SupplierForm.controls.company.value;
            this.companies$.subscribe((companies) => {
                selectedCompany = companies.find((company) => company.id === companyId);
            });
        }

        if (this.SupplierForm.valid) {
            if (this.SupplierForm.controls.id.value && this.SupplierForm.controls.name.value && this.SupplierForm.controls.email.value && this.SupplierForm.controls.mobile.value && selectedCompany) {
                this.suppliersService.updateSupplier({
                    id: this.SupplierForm.controls.id.value,
                    name: this.SupplierForm.controls.name.value,
                    email: this.SupplierForm.controls.email.value,
                    mobile: this.SupplierForm.controls.mobile.value,
                    company: {
                        id: selectedCompany.id,
                        name: selectedCompany.name,
                        address: selectedCompany.address,
                        email: selectedCompany.email,
                        mobile: selectedCompany.mobile,
                    }
                }).subscribe({
                    next: (data) => {
                        Swal.fire({
                            title: 'Success!',
                            text: 'Supplier.ts updated successfully!',
                            icon: 'success',
                        }).then((result) => {
                            if (result.isConfirmed) {
                                this.router.navigate(['/suppliers']);
                            }
                        });
                    },
                    error: (err) => {
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
}
