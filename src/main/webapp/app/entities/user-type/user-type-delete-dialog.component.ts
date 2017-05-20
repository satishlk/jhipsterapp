import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { UserType } from './user-type.model';
import { UserTypePopupService } from './user-type-popup.service';
import { UserTypeService } from './user-type.service';

@Component({
    selector: 'jhi-user-type-delete-dialog',
    templateUrl: './user-type-delete-dialog.component.html'
})
export class UserTypeDeleteDialogComponent {

    userType: UserType;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private userTypeService: UserTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['userType']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.userTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'userTypeListModification',
                content: 'Deleted an userType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-type-delete-popup',
    template: ''
})
export class UserTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private userTypePopupService: UserTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.userTypePopupService
                .open(UserTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
