import {inject} from "@angular/core";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import jwtDecode from "jwt-decode";
import {UserRoles} from "../dto/UserRoles";
import {DecodedToken} from "../dto/DecodedToken";


const checkAuthorization = (router: Router, http: HttpClient) => {
    let isAuthorized = false;

    const token = localStorage.getItem("token");
    if (token != null) {
        let decodedToken: { iss: string, sub: string, exp: number, iat: number, scope: string } = jwtDecode(token);
        let now = new Date().getTime();
        let exp = decodedToken.exp * 1000;

        if (now > exp) {
            isAuthorized = false;
        } else {
            isAuthorized = true;
        }
    }

    return isAuthorized;
}

export const AuthorizedUserGaurd = () => {
    const router = inject(Router);
    const http = inject(HttpClient);

    let isAuthorized = checkAuthorization(router, http);

    if (!isAuthorized) {
        localStorage.removeItem("token");
        router.navigate(['/auth/login']);
    }

    return isAuthorized;
}

export const UnauthorizedUserGuard = () => {
    const router = inject(Router);
    const http = inject(HttpClient);

    let isAuthorized = checkAuthorization(router, http);

    if (isAuthorized) {
        router.navigate(['/dashboard']);
    }

    return !isAuthorized;
}

export const AdminAuthGuard = () => {
    const router = inject(Router);
    const http = inject(HttpClient);
    let isAdmin: boolean = false;

    const token = localStorage.getItem("token");
    if (token != null) {
        let decodedToken: DecodedToken = jwtDecode(token);

        if (decodedToken.role == UserRoles.ADMIN) {
            isAdmin = true;
        }
    }

    if (!isAdmin) {
        router.navigate(['/dashboard'])
    }

    return isAdmin;
}
