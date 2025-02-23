import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SourceSystemComponent } from './list/source-system.component';
import { SourceSystemDetailComponent } from './detail/source-system-detail.component';
import { SourceSystemUpdateComponent } from './update/source-system-update.component';
import SourceSystemResolve from './route/source-system-routing-resolve.service';

const sourceSystemRoute: Routes = [
  {
    path: '',
    component: SourceSystemComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SourceSystemDetailComponent,
    resolve: {
      sourceSystem: SourceSystemResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SourceSystemUpdateComponent,
    resolve: {
      sourceSystem: SourceSystemResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SourceSystemUpdateComponent,
    resolve: {
      sourceSystem: SourceSystemResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default sourceSystemRoute;
