import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Vehicles } from './vehicles.model';
import { VehiclesService } from './vehicles.service';
@Injectable()
export class VehiclesPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private vehiclesService: VehiclesService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.vehiclesService.find(id).subscribe((vehicles) => {
                vehicles.addDatetime = this.datePipe
                    .transform(vehicles.addDatetime, 'yyyy-MM-ddThh:mm');
                vehicles.updateDateTime = this.datePipe
                    .transform(vehicles.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.vehiclesModalRef(component, vehicles);
            });
        } else {
            return this.vehiclesModalRef(component, new Vehicles());
        }
    }

    vehiclesModalRef(component: Component, vehicles: Vehicles): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.vehicles = vehicles;
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
