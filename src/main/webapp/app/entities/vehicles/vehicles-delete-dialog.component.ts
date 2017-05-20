import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Vehicles } from './vehicles.model';
import { VehiclesPopupService } from './vehicles-popup.service';
import { VehiclesService } from './vehicles.service';

@Component({
    selector: 'jhi-vehicles-delete-dialog',
    templateUrl: './vehicles-delete-dialog.component.html'
})
export class VehiclesDeleteDialogComponent {

    vehicles: Vehicles;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private vehiclesService: VehiclesService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['vehicles']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.vehiclesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'vehiclesListModification',
                content: 'Deleted an vehicles'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vehicles-delete-popup',
    template: ''
})
export class VehiclesDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vehiclesPopupService: VehiclesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.vehiclesPopupService
                .open(VehiclesDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
