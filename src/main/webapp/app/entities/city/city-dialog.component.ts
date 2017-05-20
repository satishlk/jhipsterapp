import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { City } from './city.model';
import { CityPopupService } from './city-popup.service';
import { CityService } from './city.service';
import { State, StateService } from '../state';
import { Location, LocationService } from '../location';

@Component({
    selector: 'jhi-city-dialog',
    templateUrl: './city-dialog.component.html'
})
export class CityDialogComponent implements OnInit {

    city: City;
    authorities: any[];
    isSaving: boolean;

    states: State[];

    locations: Location[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private cityService: CityService,
        private stateService: StateService,
        private locationService: LocationService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['city']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.stateService.query().subscribe(
            (res: Response) => { this.states = res.json(); }, (res: Response) => this.onError(res.json()));
        this.locationService.query().subscribe(
            (res: Response) => { this.locations = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.city.id !== undefined) {
            this.cityService.update(this.city)
                .subscribe((res: City) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.cityService.create(this.city)
                .subscribe((res: City) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: City) {
        this.eventManager.broadcast({ name: 'cityListModification', content: 'OK'});
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

    trackStateById(index: number, item: State) {
        return item.id;
    }

    trackLocationById(index: number, item: Location) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-city-popup',
    template: ''
})
export class CityPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cityPopupService: CityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.cityPopupService
                    .open(CityDialogComponent, params['id']);
            } else {
                this.modalRef = this.cityPopupService
                    .open(CityDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
