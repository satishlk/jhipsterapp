import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Ionic2SampleSharedModule } from '../../shared';
import {
    RateCardService,
    RateCardPopupService,
    RateCardComponent,
    RateCardDetailComponent,
    RateCardDialogComponent,
    RateCardPopupComponent,
    RateCardDeletePopupComponent,
    RateCardDeleteDialogComponent,
    rateCardRoute,
    rateCardPopupRoute,
} from './';

const ENTITY_STATES = [
    ...rateCardRoute,
    ...rateCardPopupRoute,
];

@NgModule({
    imports: [
        Ionic2SampleSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RateCardComponent,
        RateCardDetailComponent,
        RateCardDialogComponent,
        RateCardDeleteDialogComponent,
        RateCardPopupComponent,
        RateCardDeletePopupComponent,
    ],
    entryComponents: [
        RateCardComponent,
        RateCardDialogComponent,
        RateCardPopupComponent,
        RateCardDeleteDialogComponent,
        RateCardDeletePopupComponent,
    ],
    providers: [
        RateCardService,
        RateCardPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Ionic2SampleRateCardModule {}
