import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { RequestParamDictComponent } from './list/request-param-dict.component';
import { RequestParamDictDetailComponent } from './detail/request-param-dict-detail.component';
import { RequestParamDictUpdateComponent } from './update/request-param-dict-update.component';
import RequestParamDictResolve from './route/request-param-dict-routing-resolve.service';

const requestParamDictRoute: Routes = [
  {
    path: '',
    component: RequestParamDictComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RequestParamDictDetailComponent,
    resolve: {
      requestParamDict: RequestParamDictResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RequestParamDictUpdateComponent,
    resolve: {
      requestParamDict: RequestParamDictResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RequestParamDictUpdateComponent,
    resolve: {
      requestParamDict: RequestParamDictResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default requestParamDictRoute;
