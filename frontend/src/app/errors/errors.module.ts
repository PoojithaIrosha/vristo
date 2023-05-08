import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {RouterLink} from "@angular/router";


@NgModule({
    declarations: [
        PageNotFoundComponent
    ],
    imports: [
        CommonModule,
        RouterLink
    ],
    exports: [
        PageNotFoundComponent
    ]
})
export class ErrorsModule {
}
