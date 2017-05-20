import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ParkingHistory } from './parking-history.model';
import { ParkingHistoryService } from './parking-history.service';
@Injectable()
export class ParkingHistoryPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private parkingHistoryService: ParkingHistoryService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.parkingHistoryService.find(id).subscribe((parkingHistory) => {
                parkingHistory.intime = this.datePipe
                    .transform(parkingHistory.intime, 'yyyy-MM-ddThh:mm');
                parkingHistory.outTime = this.datePipe
                    .transform(parkingHistory.outTime, 'yyyy-MM-ddThh:mm');
                this.parkingHistoryModalRef(component, parkingHistory);
            });
        } else {
            return this.parkingHistoryModalRef(component, new ParkingHistory());
        }
    }

    parkingHistoryModalRef(component: Component, parkingHistory: ParkingHistory): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.parkingHistory = parkingHistory;
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
