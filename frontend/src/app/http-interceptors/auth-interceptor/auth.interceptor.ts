import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from "../../auth/auth.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private authService: AuthService) {
    }

    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        const token = localStorage.getItem('token');

        if (token != null) {
            let authReq = request.clone({setHeaders: {Authorization: `Bearer ${token}`}});
            return next.handle(authReq);
        } else {
            return next.handle(request);
        }
    }
}
