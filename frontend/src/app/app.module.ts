import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {httpInterceptorProviders} from "./http-interceptors";
import {AuthModule} from "./auth/auth.module";
import {ErrorsModule} from "./errors/errors.module";
import {CommonModule} from "@angular/common";
import {UserDashboardModule} from "./user-dashboard/user-dashboard.module";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

@NgModule({
    declarations: [
        AppComponent,
    ],
    imports: [
        CommonModule,
        BrowserModule,
        AppRoutingModule,
        AuthModule,
        ErrorsModule,
        UserDashboardModule,
        BrowserAnimationsModule,
    ],
  providers: [
    httpInterceptorProviders
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
