import {Component} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../auth.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-forgot-password',
    templateUrl: './forgot-password.component.html',
    styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {

    constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router) {
    }

    formErr: string = "";
    formSubmitted: boolean = false
    formBtnText: string = "Send Recover Email";

    forgotPasswordForm = this.formBuilder.group({
        email: ["", [Validators.required, Validators.email]],
    });

    submitForgotPasswordForm() {
        if (this.forgotPasswordForm.dirty && this.forgotPasswordForm.controls.email.value) {
            this.formSubmitted = true;
            this.authService.forgotPassword(this.forgotPasswordForm.controls.email.value).subscribe({
                next: (response) => {
                    if (response.success) {
                        this.formErr = '';
                        Swal.fire(
                            'Success!',
                            'Please check your email for password reset instructions.',
                            'success'
                        )
                    }
                },
                error: (err) => {
                    Swal.fire(
                        'Error!',
                        err.error.detail,
                        'error'
                    )
                    this.formSubmitted = false;
                    this.formBtnText = "Send Recover Email";
                }
            });
        }
    }

}
