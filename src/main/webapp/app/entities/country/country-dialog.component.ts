import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Country } from './country.model';
import { CountryPopupService } from './country-popup.service';
import { CountryService } from './country.service';
import { Location, LocationService } from '../location';

@Component({
    selector: 'jhi-country-dialog',
    templateUrl: './country-dialog.component.html'
})
export class CountryDialogComponent implements OnInit {

    country: Country;
    authorities: any[];
    isSaving: boolean;

    locations: Location[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private countryService: CountryService,
        private locationService: LocationService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['country']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.locationService.query().subscribe(
            (res: Response) => { this.locations = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.country.id !== undefined) {
            this.countryService.update(this.country)
                .subscribe((res: Country) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.countryService.create(this.country)
                .subscribe((res: Country) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Country) {
        this.eventManager.broadcast({ name: 'countryListModification', content: 'OK'});
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

    trackLocationById(index: number, item: Location) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-country-popup',
    template: ''
})
export class CountryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private countryPopupService: CountryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.countryPopupService
                    .open(CountryDialogComponent, params['id']);
            } else {
                this.modalRef = this.countryPopupService
                    .open(CountryDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
