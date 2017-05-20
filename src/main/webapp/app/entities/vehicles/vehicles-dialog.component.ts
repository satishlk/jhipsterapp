import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Vehicles } from './vehicles.model';
import { VehiclesPopupService } from './vehicles-popup.service';
import { VehiclesService } from './vehicles.service';

@Component({
    selector: 'jhi-vehicles-dialog',
    templateUrl: './vehicles-dialog.component.html'
})
export class VehiclesDialogComponent implements OnInit {

    vehicles: Vehicles;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private vehiclesService: VehiclesService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['vehicles']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.vehicles.id !== undefined) {
            this.vehiclesService.update(this.vehicles)
                .subscribe((res: Vehicles) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.vehiclesService.create(this.vehicles)
                .subscribe((res: Vehicles) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Vehicles) {
        this.eventManager.broadcast({ name: 'vehiclesListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-vehicles-popup',
    template: ''
})
export class VehiclesPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vehiclesPopupService: VehiclesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.vehiclesPopupService
                    .open(VehiclesDialogComponent, params['id']);
            } else {
                this.modalRef = this.vehiclesPopupService
                    .open(VehiclesDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
