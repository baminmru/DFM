import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DataTreeRootComponent } from './list/data-tree-root.component';
import { DataTreeRootDetailComponent } from './detail/data-tree-root-detail.component';
import { DataTreeRootUpdateComponent } from './update/data-tree-root-update.component';
import DataTreeRootResolve from './route/data-tree-root-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const dataTreeRootRoute: Routes = [
  {
    path: '',
    component: DataTreeRootComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DataTreeRootDetailComponent,
    resolve: {
      dataTreeRoot: DataTreeRootResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DataTreeRootUpdateComponent,
    resolve: {
      dataTreeRoot: DataTreeRootResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DataTreeRootUpdateComponent,
    resolve: {
      dataTreeRoot: DataTreeRootResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default dataTreeRootRoute;
