import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Ionic2SampleSharedModule } from '../../shared';
import {
    UserTypeService,
    UserTypePopupService,
    UserTypeComponent,
    UserTypeDetailComponent,
    UserTypeDialogComponent,
    UserTypePopupComponent,
    UserTypeDeletePopupComponent,
    UserTypeDeleteDialogComponent,
    userTypeRoute,
    userTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...userTypeRoute,
    ...userTypePopupRoute,
];

@NgModule({
    imports: [
        Ionic2SampleSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UserTypeComponent,
        UserTypeDetailComponent,
        UserTypeDialogComponent,
        UserTypeDeleteDialogComponent,
        UserTypePopupComponent,
        UserTypeDeletePopupComponent,
    ],
    entryComponents: [
        UserTypeComponent,
        UserTypeDialogComponent,
        UserTypePopupComponent,
        UserTypeDeleteDialogComponent,
        UserTypeDeletePopupComponent,
    ],
    providers: [
        UserTypeService,
        UserTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Ionic2SampleUserTypeModule {}
