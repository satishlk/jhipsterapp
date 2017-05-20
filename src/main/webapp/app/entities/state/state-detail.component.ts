import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { State } from './state.model';
import { StateService } from './state.service';

@Component({
    selector: 'jhi-state-detail',
    templateUrl: './state-detail.component.html'
})
export class StateDetailComponent implements OnInit, OnDestroy {

    state: State;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private stateService: StateService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['state']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInStates();
    }

    load(id) {
        this.stateService.find(id).subscribe((state) => {
            this.state = state;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInStates() {
        this.eventSubscriber = this.eventManager.subscribe('stateListModification', (response) => this.load(this.state.id));
    }
}
