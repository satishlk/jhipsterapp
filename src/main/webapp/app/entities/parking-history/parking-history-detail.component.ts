import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { ParkingHistory } from './parking-history.model';
import { ParkingHistoryService } from './parking-history.service';

@Component({
    selector: 'jhi-parking-history-detail',
    templateUrl: './parking-history-detail.component.html'
})
export class ParkingHistoryDetailComponent implements OnInit, OnDestroy {

    parkingHistory: ParkingHistory;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private parkingHistoryService: ParkingHistoryService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['parkingHistory']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInParkingHistories();
    }

    load(id) {
        this.parkingHistoryService.find(id).subscribe((parkingHistory) => {
            this.parkingHistory = parkingHistory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInParkingHistories() {
        this.eventSubscriber = this.eventManager.subscribe('parkingHistoryListModification', (response) => this.load(this.parkingHistory.id));
    }
}
