import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Users } from './users.model';
import { UsersPopupService } from './users-popup.service';
import { UsersService } from './users.service';

@Component({
    selector: 'jhi-users-delete-dialog',
    templateUrl: './users-delete-dialog.component.html'
})
export class UsersDeleteDialogComponent {

    users: Users;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private usersService: UsersService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['users']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.usersService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'usersListModification',
                content: 'Deleted an users'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-users-delete-popup',
    template: ''
})
export class UsersDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private usersPopupService: UsersPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.usersPopupService
                .open(UsersDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
