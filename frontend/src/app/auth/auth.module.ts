import {NgModule} from '@angular/core';
import {CommonModule, JsonPipe} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {AppComponent} from "../app.component";
import {LoginComponent} from "./login/login.component";
import {ForgotPasswordComponent} from "./forgot-password/forgot-password.component";
import {ResetPasswordComponent} from "./reset-password/reset-password.component";
import {AppRoutingModule} from "../app-routing.module";
import {AuthComponent} from './auth.component';
import {BrowserModule} from "@angular/platform-browser";


@NgModule({
    declarations: [
        LoginComponent,
        ForgotPasswordComponent,
        ResetPasswordComponent,
        AuthComponent
    ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        HttpClientModule,
        AppRoutingModule,
    ],
    exports: [
        LoginComponent,
        ForgotPasswordComponent,
        ResetPasswordComponent,
        AuthComponent
    ]
})
export class AuthModule {
}
