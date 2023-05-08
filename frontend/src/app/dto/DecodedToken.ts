import {UserRoles} from "./UserRoles";

export interface DecodedToken {
    iss: string;
    sub: string;
    exp: number;
    iat: number;
    role: UserRoles;
}
