import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { UserTypeComponent } from './user-type.component';
import { UserTypeDetailComponent } from './user-type-detail.component';
import { UserTypePopupComponent } from './user-type-dialog.component';
import { UserTypeDeletePopupComponent } from './user-type-delete-dialog.component';

import { Principal } from '../../shared';

export const userTypeRoute: Routes = [
  {
    path: 'user-type',
    component: UserTypeComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.userType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'user-type/:id',
    component: UserTypeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.userType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const userTypePopupRoute: Routes = [
  {
    path: 'user-type-new',
    component: UserTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.userType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'user-type/:id/edit',
    component: UserTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.userType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'user-type/:id/delete',
    component: UserTypeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.userType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
