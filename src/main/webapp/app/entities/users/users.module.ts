import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Ionic2SampleSharedModule } from '../../shared';
import {
    UsersService,
    UsersPopupService,
    UsersComponent,
    UsersDetailComponent,
    UsersDialogComponent,
    UsersPopupComponent,
    UsersDeletePopupComponent,
    UsersDeleteDialogComponent,
    usersRoute,
    usersPopupRoute,
} from './';

const ENTITY_STATES = [
    ...usersRoute,
    ...usersPopupRoute,
];

@NgModule({
    imports: [
        Ionic2SampleSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UsersComponent,
        UsersDetailComponent,
        UsersDialogComponent,
        UsersDeleteDialogComponent,
        UsersPopupComponent,
        UsersDeletePopupComponent,
    ],
    entryComponents: [
        UsersComponent,
        UsersDialogComponent,
        UsersPopupComponent,
        UsersDeleteDialogComponent,
        UsersDeletePopupComponent,
    ],
    providers: [
        UsersService,
        UsersPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Ionic2SampleUsersModule {}
