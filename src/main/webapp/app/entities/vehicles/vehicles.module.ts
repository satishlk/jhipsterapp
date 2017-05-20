import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Ionic2SampleSharedModule } from '../../shared';
import {
    VehiclesService,
    VehiclesPopupService,
    VehiclesComponent,
    VehiclesDetailComponent,
    VehiclesDialogComponent,
    VehiclesPopupComponent,
    VehiclesDeletePopupComponent,
    VehiclesDeleteDialogComponent,
    vehiclesRoute,
    vehiclesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...vehiclesRoute,
    ...vehiclesPopupRoute,
];

@NgModule({
    imports: [
        Ionic2SampleSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VehiclesComponent,
        VehiclesDetailComponent,
        VehiclesDialogComponent,
        VehiclesDeleteDialogComponent,
        VehiclesPopupComponent,
        VehiclesDeletePopupComponent,
    ],
    entryComponents: [
        VehiclesComponent,
        VehiclesDialogComponent,
        VehiclesPopupComponent,
        VehiclesDeleteDialogComponent,
        VehiclesDeletePopupComponent,
    ],
    providers: [
        VehiclesService,
        VehiclesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Ionic2SampleVehiclesModule {}
