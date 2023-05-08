import {Component} from '@angular/core';
import {UserRoles} from "../../../dto/UserRoles";
import {FormBuilder, Validators} from "@angular/forms";
import {UsersService} from "../users.service";
import Swal from "sweetalert2";
import {Router} from "@angular/router";

@Component({
    selector: 'app-add-new-user',
    templateUrl: './add-new-user.component.html',
    styleUrls: ['./add-new-user.component.css']
})
export class AddNewUserComponent {

    constructor(private formBuilder: FormBuilder, private usersService: UsersService, private router: Router) {
    }

    regiserUserForm = this.formBuilder.group({
        name: ['', [Validators.required, Validators.minLength(3)]],
        username: ['', [Validators.required, Validators.minLength(3)]],
        password: ['', [Validators.required, Validators.minLength(3)]],
        mobile: ['', [Validators.required, Validators.minLength(3)]],
        email: ['', [Validators.required, Validators.minLength(3), Validators.email]],
        role: [UserRoles.USER, Validators.required],
        isEnabled: [true, Validators.required]
    });

    registerUser() {
        if (this.regiserUserForm.valid) {
            if (this.regiserUserForm.controls.name.value && this.regiserUserForm.controls.email.value && this.regiserUserForm.controls.password.value && this.regiserUserForm.controls.username.value && this.regiserUserForm.controls.mobile.value && this.regiserUserForm.controls.role.value && this.regiserUserForm.controls.isEnabled.value) {
                this.usersService.registerUser({
                    name: this.regiserUserForm.controls.name.value,
                    username: this.regiserUserForm.controls.username.value,
                    password: this.regiserUserForm.controls.password.value,
                    mobile: this.regiserUserForm.controls.mobile.value,
                    email: this.regiserUserForm.controls.email.value,
                    role: this.regiserUserForm.controls.role.value,
                    isEnabled: this.regiserUserForm.controls.isEnabled.value
                }).subscribe({
                    next: response => {
                        Swal.fire({
                            title: 'Success!',
                            text: response.message,
                            icon: 'success',
                        }).then((result) => {
                            if (result.isConfirmed) {
                                this.regiserUserForm.reset();
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
        } else {
            Object.keys(this.regiserUserForm.controls).forEach(field => {
                const control = this.regiserUserForm.get(field);
                if (control?.invalid) {
                    control.markAsTouched({onlySelf: true});
                }
            });
            Swal.fire({
                title: 'Error!',
                text: 'Please fill all the fields',
                icon: 'error',
            })
        }
    }

    protected readonly UserRoles = UserRoles;
}
