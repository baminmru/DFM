import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DataTreeBranchComponent } from './list/data-tree-branch.component';
import { DataTreeBranchDetailComponent } from './detail/data-tree-branch-detail.component';
import { DataTreeBranchUpdateComponent } from './update/data-tree-branch-update.component';
import DataTreeBranchResolve from './route/data-tree-branch-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const dataTreeBranchRoute: Routes = [
  {
    path: '',
    component: DataTreeBranchComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DataTreeBranchDetailComponent,
    resolve: {
      dataTreeBranch: DataTreeBranchResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DataTreeBranchUpdateComponent,
    resolve: {
      dataTreeBranch: DataTreeBranchResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DataTreeBranchUpdateComponent,
    resolve: {
      dataTreeBranch: DataTreeBranchResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default dataTreeBranchRoute;
