import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { ParkingHistory } from './parking-history.model';
import { ParkingHistoryPopupService } from './parking-history-popup.service';
import { ParkingHistoryService } from './parking-history.service';

@Component({
    selector: 'jhi-parking-history-delete-dialog',
    templateUrl: './parking-history-delete-dialog.component.html'
})
export class ParkingHistoryDeleteDialogComponent {

    parkingHistory: ParkingHistory;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private parkingHistoryService: ParkingHistoryService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['parkingHistory']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.parkingHistoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'parkingHistoryListModification',
                content: 'Deleted an parkingHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-parking-history-delete-popup',
    template: ''
})
export class ParkingHistoryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parkingHistoryPopupService: ParkingHistoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.parkingHistoryPopupService
                .open(ParkingHistoryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
