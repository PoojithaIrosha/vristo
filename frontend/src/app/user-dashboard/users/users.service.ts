import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {shareReplay} from "rxjs";
import {UserDto} from "../../dto/UserDto";
import {ClientResponse} from "../../dto/ClientResponse";

@Injectable({
    providedIn: 'root'
})
export class UsersService {

    constructor(private http: HttpClient) {
    }

    updateUserStatus(userId: number) {
        return this.http.put<ClientResponse>(`http://localhost:8080/api/users/update-status/${userId}`, {});
    }

    getUserById(userId: string) {
        return this.http.get<UserDto>(`http://localhost:8080/api/users/${userId}`);
    }

    searchUsers(text: string, page: number, size: number) {
        return this.http.get<any>(`http://localhost:8080/api/users/search?text=${text}&page=${page}&size=${size}`).pipe(
            shareReplay(1)
        );
    }

    updateUserDetails(user: UserDto) {
        return this.http.put<ClientResponse>(`http://localhost:8080/api/users`, user);
    }

    registerUser(user: {
        name: string;
        username: string;
        password: string;
        mobile: string;
        email: string;
        role: string;
        isEnabled: boolean;
    }) {
        return this.http.post<ClientResponse>(`http://localhost:8080/api/users`, user);
    }
}
