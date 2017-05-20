import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { Ionic2SampleCountryModule } from './country/country.module';
import { Ionic2SampleStateModule } from './state/state.module';
import { Ionic2SampleCityModule } from './city/city.module';
import { Ionic2SampleLocationModule } from './location/location.module';
import { Ionic2SampleUserTypeModule } from './user-type/user-type.module';
import { Ionic2SampleUsersModule } from './users/users.module';
import { Ionic2SampleParkingHistoryModule } from './parking-history/parking-history.module';
import { Ionic2SampleVehiclesModule } from './vehicles/vehicles.module';
import { Ionic2SampleRateCardModule } from './rate-card/rate-card.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        Ionic2SampleCountryModule,
        Ionic2SampleStateModule,
        Ionic2SampleCityModule,
        Ionic2SampleLocationModule,
        Ionic2SampleUserTypeModule,
        Ionic2SampleUsersModule,
        Ionic2SampleParkingHistoryModule,
        Ionic2SampleVehiclesModule,
        Ionic2SampleRateCardModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Ionic2SampleEntityModule {}
