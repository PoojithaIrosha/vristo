import {Component, OnInit} from '@angular/core';
import {UserDto} from "../../../dto/UserDto";
import {UsersService} from "../users.service";
import {Router} from "@angular/router";
import {FormBuilder} from "@angular/forms";
import {Location} from "@angular/common";
import Swal from "sweetalert2";

@Component({
    selector: 'app-all-users',
    templateUrl: './all-users.component.html',
    styleUrls: ['./all-users.component.css']
})
export class AllUsersComponent implements OnInit {

    userList!: UserDto[];
    totalPages: number[] = [];
    totalElements!: number;
    currentPage: number = 0;
    sizesList: number[] = [10, 20, 30, 40, 50]
    sizeNum: number = this.sizesList[0];
    last: boolean = false;
    offset!: number;

    searchForm = this.formBuilder.group({
        search: ['']
    });

    constructor(private usersService: UsersService, private router: Router, private formBuilder: FormBuilder, private _location: Location) {
    }

    ngOnInit() {
        this.searchUsers(0, this.sizeNum);
    }

    paginate(page: number) {
        this.searchUsers(page, this.sizeNum);
    }

    sizeChange(size: string) {
        this.sizeNum = parseInt(size);
        this.paginate(this.currentPage);
    }

    searchUsers(page: number, size: number) {
        let searchText = '';
        if (this.searchForm.controls.search.value) {
            searchText = this.searchForm.controls.search.value;
        }

        this.usersService.searchUsers(searchText, page, size).subscribe(data => {
            this.totalPages = [];
            for (let i = 1; i <= data.totalPages; i++) {
                this.totalPages[i - 1] = i;
            }
            this.currentPage = data.pageable.pageNumber;
            this.totalElements = data.totalElements;
            this.last = data.last;
            this.offset = data.pageable.offset;
            this.userList = data.content;
        });
    }

    updateUserStatus(userId: number) {
        Swal.fire({
            title: 'Update the status of this user?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#E7515A',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, update it!'
        }).then((result) => {
            if (result.isConfirmed) {
                this.usersService.updateUserStatus(userId).subscribe({
                    next: (data) => {
                        Swal.fire(
                            "Success!",
                            data.message,
                            "success");
                    },
                    error: (err) => {
                        Swal.fire(
                            "Error!",
                            "Something went wrong. Please try again later.",
                            "error");
                    }
                });
            } else {
                this.router.navigateByUrl("/users", {skipLocationChange: true}).then(() => {
                    console.log(decodeURI(this._location.path()));
                    this.router.navigate([decodeURI(this._location.path())]);
                });
            }
        });
    }
}

