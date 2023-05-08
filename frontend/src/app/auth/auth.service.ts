import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ClientResponse} from "../dto/ClientResponse";
import {LoginResponse} from "../dto/LoginResponse";

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    constructor(private http: HttpClient) {
    }

    login(username: string, password: string) {
        return this.http.post<LoginResponse>("http://localhost:8080/api/auth/login", {
            username: username,
            password: password
        });
    }

    forgotPassword(email: string) {
        return this.http.get<ClientResponse>("http://localhost:8080/api/auth/forgot-password?email=" + email);
    }

    resetPassword(email: string, verificationCode: string, newPassword: string) {
        return this.http.post<ClientResponse>("http://localhost:8080/api/auth/reset-password", {
            "email": email,
            "verificationCode": verificationCode,
            "password": newPassword,
        });
    }
}
