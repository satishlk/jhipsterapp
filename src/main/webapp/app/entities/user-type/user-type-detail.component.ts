import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { UserType } from './user-type.model';
import { UserTypeService } from './user-type.service';

@Component({
    selector: 'jhi-user-type-detail',
    templateUrl: './user-type-detail.component.html'
})
export class UserTypeDetailComponent implements OnInit, OnDestroy {

    userType: UserType;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private userTypeService: UserTypeService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['userType']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUserTypes();
    }

    load(id) {
        this.userTypeService.find(id).subscribe((userType) => {
            this.userType = userType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUserTypes() {
        this.eventSubscriber = this.eventManager.subscribe('userTypeListModification', (response) => this.load(this.userType.id));
    }
}
