import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { RequestConfigComponent } from './list/request-config.component';
import { RequestConfigDetailComponent } from './detail/request-config-detail.component';
import { RequestConfigUpdateComponent } from './update/request-config-update.component';
import RequestConfigResolve from './route/request-config-routing-resolve.service';

const requestConfigRoute: Routes = [
  {
    path: '',
    component: RequestConfigComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RequestConfigDetailComponent,
    resolve: {
      requestConfig: RequestConfigResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RequestConfigUpdateComponent,
    resolve: {
      requestConfig: RequestConfigResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RequestConfigUpdateComponent,
    resolve: {
      requestConfig: RequestConfigResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default requestConfigRoute;
