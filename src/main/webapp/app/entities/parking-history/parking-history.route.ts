import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ParkingHistoryComponent } from './parking-history.component';
import { ParkingHistoryDetailComponent } from './parking-history-detail.component';
import { ParkingHistoryPopupComponent } from './parking-history-dialog.component';
import { ParkingHistoryDeletePopupComponent } from './parking-history-delete-dialog.component';

import { Principal } from '../../shared';

export const parkingHistoryRoute: Routes = [
  {
    path: 'parking-history',
    component: ParkingHistoryComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.parkingHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'parking-history/:id',
    component: ParkingHistoryDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.parkingHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const parkingHistoryPopupRoute: Routes = [
  {
    path: 'parking-history-new',
    component: ParkingHistoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.parkingHistory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'parking-history/:id/edit',
    component: ParkingHistoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.parkingHistory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'parking-history/:id/delete',
    component: ParkingHistoryDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.parkingHistory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
