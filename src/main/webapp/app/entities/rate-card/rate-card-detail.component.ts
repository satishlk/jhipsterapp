import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { RateCard } from './rate-card.model';
import { RateCardService } from './rate-card.service';

@Component({
    selector: 'jhi-rate-card-detail',
    templateUrl: './rate-card-detail.component.html'
})
export class RateCardDetailComponent implements OnInit, OnDestroy {

    rateCard: RateCard;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private rateCardService: RateCardService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['rateCard']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRateCards();
    }

    load(id) {
        this.rateCardService.find(id).subscribe((rateCard) => {
            this.rateCard = rateCard;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRateCards() {
        this.eventSubscriber = this.eventManager.subscribe('rateCardListModification', (response) => this.load(this.rateCard.id));
    }
}
