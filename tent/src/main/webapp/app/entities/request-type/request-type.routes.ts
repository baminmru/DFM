import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { RequestTypeComponent } from './list/request-type.component';
import { RequestTypeDetailComponent } from './detail/request-type-detail.component';
import { RequestTypeUpdateComponent } from './update/request-type-update.component';
import RequestTypeResolve from './route/request-type-routing-resolve.service';

const requestTypeRoute: Routes = [
  {
    path: '',
    component: RequestTypeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RequestTypeDetailComponent,
    resolve: {
      requestType: RequestTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RequestTypeUpdateComponent,
    resolve: {
      requestType: RequestTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RequestTypeUpdateComponent,
    resolve: {
      requestType: RequestTypeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default requestTypeRoute;
