import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { UserType } from './user-type.model';
import { UserTypePopupService } from './user-type-popup.service';
import { UserTypeService } from './user-type.service';

@Component({
    selector: 'jhi-user-type-dialog',
    templateUrl: './user-type-dialog.component.html'
})
export class UserTypeDialogComponent implements OnInit {

    userType: UserType;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private userTypeService: UserTypeService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['userType']);
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
        if (this.userType.id !== undefined) {
            this.userTypeService.update(this.userType)
                .subscribe((res: UserType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.userTypeService.create(this.userType)
                .subscribe((res: UserType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: UserType) {
        this.eventManager.broadcast({ name: 'userTypeListModification', content: 'OK'});
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
    selector: 'jhi-user-type-popup',
    template: ''
})
export class UserTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userTypePopupService: UserTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.userTypePopupService
                    .open(UserTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.userTypePopupService
                    .open(UserTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
