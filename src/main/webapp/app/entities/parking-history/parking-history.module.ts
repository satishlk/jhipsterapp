import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Ionic2SampleSharedModule } from '../../shared';
import {
    ParkingHistoryService,
    ParkingHistoryPopupService,
    ParkingHistoryComponent,
    ParkingHistoryDetailComponent,
    ParkingHistoryDialogComponent,
    ParkingHistoryPopupComponent,
    ParkingHistoryDeletePopupComponent,
    ParkingHistoryDeleteDialogComponent,
    parkingHistoryRoute,
    parkingHistoryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...parkingHistoryRoute,
    ...parkingHistoryPopupRoute,
];

@NgModule({
    imports: [
        Ionic2SampleSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ParkingHistoryComponent,
        ParkingHistoryDetailComponent,
        ParkingHistoryDialogComponent,
        ParkingHistoryDeleteDialogComponent,
        ParkingHistoryPopupComponent,
        ParkingHistoryDeletePopupComponent,
    ],
    entryComponents: [
        ParkingHistoryComponent,
        ParkingHistoryDialogComponent,
        ParkingHistoryPopupComponent,
        ParkingHistoryDeleteDialogComponent,
        ParkingHistoryDeletePopupComponent,
    ],
    providers: [
        ParkingHistoryService,
        ParkingHistoryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Ionic2SampleParkingHistoryModule {}
