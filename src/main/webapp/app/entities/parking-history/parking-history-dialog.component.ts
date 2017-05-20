import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ParkingHistory } from './parking-history.model';
import { ParkingHistoryPopupService } from './parking-history-popup.service';
import { ParkingHistoryService } from './parking-history.service';

@Component({
    selector: 'jhi-parking-history-dialog',
    templateUrl: './parking-history-dialog.component.html'
})
export class ParkingHistoryDialogComponent implements OnInit {

    parkingHistory: ParkingHistory;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private parkingHistoryService: ParkingHistoryService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['parkingHistory']);
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
        if (this.parkingHistory.id !== undefined) {
            this.parkingHistoryService.update(this.parkingHistory)
                .subscribe((res: ParkingHistory) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.parkingHistoryService.create(this.parkingHistory)
                .subscribe((res: ParkingHistory) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ParkingHistory) {
        this.eventManager.broadcast({ name: 'parkingHistoryListModification', content: 'OK'});
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
    selector: 'jhi-parking-history-popup',
    template: ''
})
export class ParkingHistoryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parkingHistoryPopupService: ParkingHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.parkingHistoryPopupService
                    .open(ParkingHistoryDialogComponent, params['id']);
            } else {
                this.modalRef = this.parkingHistoryPopupService
                    .open(ParkingHistoryDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
