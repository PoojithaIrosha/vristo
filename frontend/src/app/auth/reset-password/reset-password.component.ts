import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../auth.service";
import Swal from "sweetalert2";

@Component({
    selector: 'app-reset-password',
    templateUrl: './reset-password.component.html',
    styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

    formErr: string = "";
    submitBtnText: string = "Reset Password";
    formSubmitted: boolean = false;

    constructor(private fb: FormBuilder, private route: ActivatedRoute, private authService: AuthService, private router: Router) {
    }

    resetPasswordForm = this.fb.group({
        userEmail: ['', [Validators.required, Validators.email]],
        verificationCode: ['', [Validators.required]],
        newPassword: ['', [Validators.required, Validators.minLength(3)]],
        confirmPassword: ['', [Validators.required, Validators.minLength(3)]]
    });

    ngOnInit(): void {
        this.resetPasswordForm.controls.userEmail.setValue(this.route.snapshot.queryParamMap.get("email"));
        this.resetPasswordForm.controls.verificationCode.setValue(this.route.snapshot.queryParamMap.get("verificationCode"));
    }

    resetPassword() {
        this.formSubmitted = true;
        if (this.resetPasswordForm.valid && this.resetPasswordForm.touched) {
            if (this.resetPasswordForm.controls.userEmail.value && this.resetPasswordForm.controls.verificationCode.value && this.resetPasswordForm.controls.newPassword.value && this.resetPasswordForm.controls.confirmPassword.value) {
                if (this.resetPasswordForm.controls.newPassword.value != this.resetPasswordForm.controls.confirmPassword.value) {
                    this.formErr = "Passwords does not match."
                    return;
                }
                this.authService.resetPassword(this.resetPasswordForm.controls.userEmail.value, this.resetPasswordForm.controls.verificationCode.value, this.resetPasswordForm.controls.newPassword.value).subscribe({
                    next: data => {
                        this.formErr = '';
                        Swal.fire({
                            icon: 'success',
                            title: 'Password reset successfully',
                            text: 'You can now login with your new password.',
                        }).then((a) => {
                            a.isConfirmed ? this.router.navigate(['/auth/login']) : '';
                        });
                    },
                    error: err => {
                        Swal.fire({
                            icon: 'error',
                            title: 'Request a new password reset link',
                            text: err.error.detail,
                        }).then((a) => {
                            a.isConfirmed ? this.router.navigate(['/auth/forgot-password']) : '';
                        });
                        this.formSubmitted = false;
                    }
                });
            }
        }

    }


}
