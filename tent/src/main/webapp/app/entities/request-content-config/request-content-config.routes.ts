import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { RequestContentConfigComponent } from './list/request-content-config.component';
import { RequestContentConfigDetailComponent } from './detail/request-content-config-detail.component';
import { RequestContentConfigUpdateComponent } from './update/request-content-config-update.component';
import RequestContentConfigResolve from './route/request-content-config-routing-resolve.service';

const requestContentConfigRoute: Routes = [
  {
    path: '',
    component: RequestContentConfigComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RequestContentConfigDetailComponent,
    resolve: {
      requestContentConfig: RequestContentConfigResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RequestContentConfigUpdateComponent,
    resolve: {
      requestContentConfig: RequestContentConfigResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RequestContentConfigUpdateComponent,
    resolve: {
      requestContentConfig: RequestContentConfigResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default requestContentConfigRoute;
