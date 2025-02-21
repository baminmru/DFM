import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { RequestInfoComponent } from './list/request-info.component';
import { RequestInfoDetailComponent } from './detail/request-info-detail.component';
import { RequestInfoUpdateComponent } from './update/request-info-update.component';
import RequestInfoResolve from './route/request-info-routing-resolve.service';

const requestInfoRoute: Routes = [
  {
    path: '',
    component: RequestInfoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RequestInfoDetailComponent,
    resolve: {
      requestInfo: RequestInfoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RequestInfoUpdateComponent,
    resolve: {
      requestInfo: RequestInfoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RequestInfoUpdateComponent,
    resolve: {
      requestInfo: RequestInfoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default requestInfoRoute;
