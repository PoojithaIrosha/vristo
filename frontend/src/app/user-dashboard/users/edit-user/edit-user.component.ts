import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import Swal from "sweetalert2";
import {UsersService} from "../users.service";
import {UserRoles} from "../../../dto/UserRoles";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
    selector: 'app-edit-user',
    templateUrl: './edit-user.component.html',
    styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

    constructor(private route: ActivatedRoute, private userService: UsersService, private router: Router, private formBuilder: FormBuilder) {

    }

    updateUserForm = this.formBuilder.group({
        id: [{value: 1, disabled: true}, [Validators.required]],
        name: ["", [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
        mobile: ["", [Validators.required, Validators.minLength(10), Validators.maxLength(10)]],
        email: ["", [Validators.required, Validators.email]],
        role: ["", [Validators.required]],
        createdAt: [{value: "", disabled: true}, [Validators.required]],
        isEnabled: [{value: false, disabled: true}, [Validators.required]]
    });

    ngOnInit() {
        let userId = this.route.snapshot.paramMap.get("userId");

        if (userId != null) {
            this.userService.getUserById(userId).subscribe({
                next: (data) => {
                    this.updateUserForm.controls.id.setValue(data.id);
                    this.updateUserForm.controls.name.setValue(data.name);
                    this.updateUserForm.controls.mobile.setValue(data.mobile);
                    this.updateUserForm.controls.email.setValue(data.email);
                    this.updateUserForm.controls.role.setValue(data.role);
                    this.updateUserForm.controls.createdAt.setValue(data.createdAt);
                    this.updateUserForm.controls.isEnabled.setValue(data.isEnabled);
                },
                error: (err) => {
                    Swal.fire(
                        "Error",
                        "Something went wrong. Please try again later.",
                        "error"
                    ).then((result) => {
                        if (result.isConfirmed) {
                            this.router.navigate(["/users"])
                        }
                    })
                }
            });
        }
    }

    updateUser() {
        if (this.updateUserForm.valid) {
            if (this.updateUserForm.controls.id.value && this.updateUserForm.controls.name.value && this.updateUserForm.controls.mobile.value && this.updateUserForm.controls.email.value && this.updateUserForm.controls.role.value && this.updateUserForm.controls.createdAt.value && this.updateUserForm.controls.isEnabled.value) {
                console.log()
                this.userService.updateUserDetails({
                    id: this.updateUserForm.controls.id.value,
                    name: this.updateUserForm.controls.name.value,
                    mobile: this.updateUserForm.controls.mobile.value,
                    email: this.updateUserForm.controls.email.value,
                    role: this.updateUserForm.controls.role.value,
                    createdAt: this.updateUserForm.controls.createdAt.value,
                    isEnabled: this.updateUserForm.controls.isEnabled.value
                }).subscribe({
                    next: data => {
                        Swal.fire({
                            title: "Success",
                            text: data.message,
                            icon: "success",
                        }).then((result) => {
                            if (result.isConfirmed) {
                                this.router.navigate(["/users"])
                            }
                        })
                    },
                    error: err => {
                        Swal.fire({
                            title: "Error",
                            text: err.error.detail,
                            icon: "error",
                        })
                    }
                });
            }
        } else {
            Swal.fire({
                title: "Error",
                text: "Please fill all the fields correctly",
                icon: "error",
            })
        }
    }

    protected readonly UserRoles = UserRoles;
}
