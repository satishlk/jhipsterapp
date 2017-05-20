import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { RateCardComponent } from './rate-card.component';
import { RateCardDetailComponent } from './rate-card-detail.component';
import { RateCardPopupComponent } from './rate-card-dialog.component';
import { RateCardDeletePopupComponent } from './rate-card-delete-dialog.component';

import { Principal } from '../../shared';

export const rateCardRoute: Routes = [
  {
    path: 'rate-card',
    component: RateCardComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.rateCard.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'rate-card/:id',
    component: RateCardDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.rateCard.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const rateCardPopupRoute: Routes = [
  {
    path: 'rate-card-new',
    component: RateCardPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.rateCard.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'rate-card/:id/edit',
    component: RateCardPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.rateCard.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'rate-card/:id/delete',
    component: RateCardDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ionic2SampleApp.rateCard.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
