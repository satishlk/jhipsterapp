import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { RateCard } from './rate-card.model';
import { RateCardPopupService } from './rate-card-popup.service';
import { RateCardService } from './rate-card.service';

@Component({
    selector: 'jhi-rate-card-delete-dialog',
    templateUrl: './rate-card-delete-dialog.component.html'
})
export class RateCardDeleteDialogComponent {

    rateCard: RateCard;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private rateCardService: RateCardService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['rateCard']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.rateCardService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'rateCardListModification',
                content: 'Deleted an rateCard'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-rate-card-delete-popup',
    template: ''
})
export class RateCardDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rateCardPopupService: RateCardPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.rateCardPopupService
                .open(RateCardDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
