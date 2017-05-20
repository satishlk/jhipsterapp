import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { ParkingHistory } from './parking-history.model';
import { ParkingHistoryService } from './parking-history.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-parking-history',
    templateUrl: './parking-history.component.html'
})
export class ParkingHistoryComponent implements OnInit, OnDestroy {
parkingHistories: ParkingHistory[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private parkingHistoryService: ParkingHistoryService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['parkingHistory']);
    }

    loadAll() {
        this.parkingHistoryService.query().subscribe(
            (res: Response) => {
                this.parkingHistories = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInParkingHistories();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ParkingHistory) {
        return item.id;
    }
    registerChangeInParkingHistories() {
        this.eventSubscriber = this.eventManager.subscribe('parkingHistoryListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
