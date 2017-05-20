import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Users } from './users.model';
import { UsersPopupService } from './users-popup.service';
import { UsersService } from './users.service';
import { UserType, UserTypeService } from '../user-type';
import { City, CityService } from '../city';
import { State, StateService } from '../state';
import { Country, CountryService } from '../country';
import { Location, LocationService } from '../location';

@Component({
    selector: 'jhi-users-dialog',
    templateUrl: './users-dialog.component.html'
})
export class UsersDialogComponent implements OnInit {

    users: Users;
    authorities: any[];
    isSaving: boolean;

    types: UserType[];

    cities: City[];

    states: State[];

    countries: Country[];

    locations: Location[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private usersService: UsersService,
        private userTypeService: UserTypeService,
        private cityService: CityService,
        private stateService: StateService,
        private countryService: CountryService,
        private locationService: LocationService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['users']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userTypeService.query({filter: 'users-is-null'}).subscribe((res: Response) => {
            if (!this.users.type || !this.users.type.id) {
                this.types = res.json();
            } else {
                this.userTypeService.find(this.users.type.id).subscribe((subRes: UserType) => {
                    this.types = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.cityService.query({filter: 'users-is-null'}).subscribe((res: Response) => {
            if (!this.users.city || !this.users.city.id) {
                this.cities = res.json();
            } else {
                this.cityService.find(this.users.city.id).subscribe((subRes: City) => {
                    this.cities = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.stateService.query({filter: 'users-is-null'}).subscribe((res: Response) => {
            if (!this.users.state || !this.users.state.id) {
                this.states = res.json();
            } else {
                this.stateService.find(this.users.state.id).subscribe((subRes: State) => {
                    this.states = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.countryService.query({filter: 'users-is-null'}).subscribe((res: Response) => {
            if (!this.users.country || !this.users.country.id) {
                this.countries = res.json();
            } else {
                this.countryService.find(this.users.country.id).subscribe((subRes: Country) => {
                    this.countries = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.locationService.query({filter: 'users-is-null'}).subscribe((res: Response) => {
            if (!this.users.location || !this.users.location.id) {
                this.locations = res.json();
            } else {
                this.locationService.find(this.users.location.id).subscribe((subRes: Location) => {
                    this.locations = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.users.id !== undefined) {
            this.usersService.update(this.users)
                .subscribe((res: Users) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.usersService.create(this.users)
                .subscribe((res: Users) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Users) {
        this.eventManager.broadcast({ name: 'usersListModification', content: 'OK'});
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

    trackUserTypeById(index: number, item: UserType) {
        return item.id;
    }

    trackCityById(index: number, item: City) {
        return item.id;
    }

    trackStateById(index: number, item: State) {
        return item.id;
    }

    trackCountryById(index: number, item: Country) {
        return item.id;
    }

    trackLocationById(index: number, item: Location) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-users-popup',
    template: ''
})
export class UsersPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private usersPopupService: UsersPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.usersPopupService
                    .open(UsersDialogComponent, params['id']);
            } else {
                this.modalRef = this.usersPopupService
                    .open(UsersDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
