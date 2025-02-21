import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { RequestContentComponent } from './list/request-content.component';
import { RequestContentDetailComponent } from './detail/request-content-detail.component';
import { RequestContentUpdateComponent } from './update/request-content-update.component';
import RequestContentResolve from './route/request-content-routing-resolve.service';

const requestContentRoute: Routes = [
  {
    path: '',
    component: RequestContentComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RequestContentDetailComponent,
    resolve: {
      requestContent: RequestContentResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RequestContentUpdateComponent,
    resolve: {
      requestContent: RequestContentResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RequestContentUpdateComponent,
    resolve: {
      requestContent: RequestContentResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default requestContentRoute;
