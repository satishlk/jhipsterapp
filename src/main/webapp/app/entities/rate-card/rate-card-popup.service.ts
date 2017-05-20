import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RateCard } from './rate-card.model';
import { RateCardService } from './rate-card.service';
@Injectable()
export class RateCardPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private rateCardService: RateCardService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.rateCardService.find(id).subscribe((rateCard) => {
                rateCard.addDatetime = this.datePipe
                    .transform(rateCard.addDatetime, 'yyyy-MM-ddThh:mm');
                rateCard.updateDateTime = this.datePipe
                    .transform(rateCard.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.rateCardModalRef(component, rateCard);
            });
        } else {
            return this.rateCardModalRef(component, new RateCard());
        }
    }

    rateCardModalRef(component: Component, rateCard: RateCard): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.rateCard = rateCard;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
