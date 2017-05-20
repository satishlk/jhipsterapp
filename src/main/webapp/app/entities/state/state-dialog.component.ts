import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { State } from './state.model';
import { StatePopupService } from './state-popup.service';
import { StateService } from './state.service';
import { Country, CountryService } from '../country';
import { Location, LocationService } from '../location';

@Component({
    selector: 'jhi-state-dialog',
    templateUrl: './state-dialog.component.html'
})
export class StateDialogComponent implements OnInit {

    state: State;
    authorities: any[];
    isSaving: boolean;

    countries: Country[];

    locations: Location[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private stateService: StateService,
        private countryService: CountryService,
        private locationService: LocationService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['state']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.countryService.query().subscribe(
            (res: Response) => { this.countries = res.json(); }, (res: Response) => this.onError(res.json()));
        this.locationService.query().subscribe(
            (res: Response) => { this.locations = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.state.id !== undefined) {
            this.stateService.update(this.state)
                .subscribe((res: State) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.stateService.create(this.state)
                .subscribe((res: State) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: State) {
        this.eventManager.broadcast({ name: 'stateListModification', content: 'OK'});
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

    trackCountryById(index: number, item: Country) {
        return item.id;
    }

    trackLocationById(index: number, item: Location) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-state-popup',
    template: ''
})
export class StatePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private statePopupService: StatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.statePopupService
                    .open(StateDialogComponent, params['id']);
            } else {
                this.modalRef = this.statePopupService
                    .open(StateDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
