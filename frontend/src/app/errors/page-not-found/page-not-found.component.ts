import {Component, Input} from '@angular/core';

@Component({
    selector: 'app-page-not-found',
    templateUrl: './page-not-found.component.html',
    styleUrls: ['./page-not-found.component.css']
})
export class PageNotFoundComponent {

    @Input() errorCode: string = "404";
    @Input() errorMessage: string = "The page you requested was not found!";
}
