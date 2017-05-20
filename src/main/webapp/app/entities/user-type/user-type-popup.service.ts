import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { UserType } from './user-type.model';
import { UserTypeService } from './user-type.service';
@Injectable()
export class UserTypePopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private userTypeService: UserTypeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.userTypeService.find(id).subscribe((userType) => {
                userType.addDatetime = this.datePipe
                    .transform(userType.addDatetime, 'yyyy-MM-ddThh:mm');
                userType.updateDateTime = this.datePipe
                    .transform(userType.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.userTypeModalRef(component, userType);
            });
        } else {
            return this.userTypeModalRef(component, new UserType());
        }
    }

    userTypeModalRef(component: Component, userType: UserType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.userType = userType;
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
