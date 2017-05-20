import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Users } from './users.model';
import { UsersService } from './users.service';

@Component({
    selector: 'jhi-users-detail',
    templateUrl: './users-detail.component.html'
})
export class UsersDetailComponent implements OnInit, OnDestroy {

    users: Users;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private usersService: UsersService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['users']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUsers();
    }

    load(id) {
        this.usersService.find(id).subscribe((users) => {
            this.users = users;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUsers() {
        this.eventSubscriber = this.eventManager.subscribe('usersListModification', (response) => this.load(this.users.id));
    }
}
