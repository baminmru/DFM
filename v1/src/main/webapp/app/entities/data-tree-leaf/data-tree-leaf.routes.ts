import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DataTreeLeafComponent } from './list/data-tree-leaf.component';
import { DataTreeLeafDetailComponent } from './detail/data-tree-leaf-detail.component';
import { DataTreeLeafUpdateComponent } from './update/data-tree-leaf-update.component';
import DataTreeLeafResolve from './route/data-tree-leaf-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const dataTreeLeafRoute: Routes = [
  {
    path: '',
    component: DataTreeLeafComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DataTreeLeafDetailComponent,
    resolve: {
      dataTreeLeaf: DataTreeLeafResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DataTreeLeafUpdateComponent,
    resolve: {
      dataTreeLeaf: DataTreeLeafResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DataTreeLeafUpdateComponent,
    resolve: {
      dataTreeLeaf: DataTreeLeafResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default dataTreeLeafRoute;
