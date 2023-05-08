import {Component} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

    formError: string = "";
    formControlErrorClass = "has-error";

    constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    }

  loginForm = this.fb.group({
    email: ["", [Validators.required]],
    password: ["", [Validators.required, Validators.minLength(3)]],
  });

  submitLoginForm() {
    if (this.loginForm.valid && this.loginForm.dirty) {
        if (this.loginForm.controls.email.value && this.loginForm.controls.password.value) {
            this.authService.login(this.loginForm.controls.email.value, this.loginForm.controls.password.value).subscribe({
                next: (data) => {
                    if (data.success) {
                        localStorage.setItem("token", data.token);
                        this.router.navigate(["/dashboard"]);
                    }
                },
                error: (err) => {
                    this.formError = err.error.detail;
                }
            });
        }
    }
  }
}
