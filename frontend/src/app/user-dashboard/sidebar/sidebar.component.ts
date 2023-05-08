import {Component, OnInit} from '@angular/core';
import jwtDecode from "jwt-decode";
import {DecodedToken} from "../../dto/DecodedToken";
import {UserRoles} from "../../dto/UserRoles";

@Component({
    selector: 'app-dashboard-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

    isAdmin: boolean = false;
    activeElement!: any;

    ngOnInit() {
        const token = localStorage.getItem("token");
        if (token != null) {
            let decodedToken: DecodedToken = jwtDecode(token);
            if (decodedToken.role == UserRoles.ADMIN) {
                this.isAdmin = true;
            }
        }
    }

    expandSideBar(menu: HTMLUListElement) {
        menu.hidden = !menu.hidden;
    }

    changeActiveElement(element: any) {
        this.activeElement.hidden = this.activeElement instanceof HTMLUListElement;
        this.activeElement = element;
    }
}
