import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Users } from './users.model';
import { UsersService } from './users.service';
@Injectable()
export class UsersPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private usersService: UsersService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.usersService.find(id).subscribe((users) => {
                users.addDatetime = this.datePipe
                    .transform(users.addDatetime, 'yyyy-MM-ddThh:mm');
                users.updateDateTime = this.datePipe
                    .transform(users.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.usersModalRef(component, users);
            });
        } else {
            return this.usersModalRef(component, new Users());
        }
    }

    usersModalRef(component: Component, users: Users): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.users = users;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
