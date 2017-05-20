import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Vehicles } from './vehicles.model';
import { VehiclesService } from './vehicles.service';

@Component({
    selector: 'jhi-vehicles-detail',
    templateUrl: './vehicles-detail.component.html'
})
export class VehiclesDetailComponent implements OnInit, OnDestroy {

    vehicles: Vehicles;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private vehiclesService: VehiclesService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['vehicles']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVehicles();
    }

    load(id) {
        this.vehiclesService.find(id).subscribe((vehicles) => {
            this.vehicles = vehicles;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVehicles() {
        this.eventSubscriber = this.eventManager.subscribe('vehiclesListModification', (response) => this.load(this.vehicles.id));
    }
}
