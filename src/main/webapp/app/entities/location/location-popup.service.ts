import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Location } from './location.model';
import { LocationService } from './location.service';
@Injectable()
export class LocationPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private locationService: LocationService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.locationService.find(id).subscribe((location) => {
                location.addDatetime = this.datePipe
                    .transform(location.addDatetime, 'yyyy-MM-ddThh:mm');
                location.updateDateTime = this.datePipe
                    .transform(location.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.locationModalRef(component, location);
            });
        } else {
            return this.locationModalRef(component, new Location());
        }
    }

    locationModalRef(component: Component, location: Location): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.location = location;
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
