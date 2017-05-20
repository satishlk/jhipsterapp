import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { RateCard } from './rate-card.model';
import { RateCardPopupService } from './rate-card-popup.service';
import { RateCardService } from './rate-card.service';
import { Vehicles, VehiclesService } from '../vehicles';

@Component({
    selector: 'jhi-rate-card-dialog',
    templateUrl: './rate-card-dialog.component.html'
})
export class RateCardDialogComponent implements OnInit {

    rateCard: RateCard;
    authorities: any[];
    isSaving: boolean;



    vehicles: Vehicles[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private rateCardService: RateCardService,
        private vehiclesService: VehiclesService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['rateCard']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.vehiclesService.query({filter: 'ratecard-is-null'}).subscribe((res: Response) => {
            if (!this.rateCard.vehicle || !this.rateCard.vehicle.id) {
                this.vehicles = res.json();
            } else {
                this.vehiclesService.find(this.rateCard.vehicle.id).subscribe((subRes: Vehicles) => {
                    this.vehicles = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.vehiclesService.query({filter: 'ratecard-is-null'}).subscribe((res: Response) => {
            if (!this.rateCard.vehicles || !this.rateCard.vehicles.id) {
                this.vehicles = res.json();
            } else {
                this.vehiclesService.find(this.rateCard.vehicles.id).subscribe((subRes: Vehicles) => {
                    this.vehicles = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rateCard.id !== undefined) {
            this.rateCardService.update(this.rateCard)
                .subscribe((res: RateCard) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.rateCardService.create(this.rateCard)
                .subscribe((res: RateCard) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: RateCard) {
        this.eventManager.broadcast({ name: 'rateCardListModification', content: 'OK'});
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

    trackVehiclesById(index: number, item: Vehicles) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rate-card-popup',
    template: ''
})
export class RateCardPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rateCardPopupService: RateCardPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.rateCardPopupService
                    .open(RateCardDialogComponent, params['id']);
            } else {
                this.modalRef = this.rateCardPopupService
                    .open(RateCardDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
