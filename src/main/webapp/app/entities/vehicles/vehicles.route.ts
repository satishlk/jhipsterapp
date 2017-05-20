import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { VehiclesComponent } from './vehicles.component';
import { VehiclesDetailComponent } from './vehicles-detail.component';
import { VehiclesPopupComponent } from './vehicles-dialog.component';
import { VehiclesDeletePopupComponent } from './vehicles-delete-dialog.component';

import { Principal } from '../../shared';

export const vehiclesRoute: Routes = [
  {
    path: 'vehicles',
    component: VehiclesComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.vehicles.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'vehicles/:id',
    component: VehiclesDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.vehicles.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const vehiclesPopupRoute: Routes = [
  {
    path: 'vehicles-new',
    component: VehiclesPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.vehicles.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'vehicles/:id/edit',
    component: VehiclesPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.vehicles.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'vehicles/:id/delete',
    component: VehiclesDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.vehicles.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
