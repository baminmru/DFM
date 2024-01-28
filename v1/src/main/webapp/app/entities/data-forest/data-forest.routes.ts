import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DataForestComponent } from './list/data-forest.component';
import { DataForestDetailComponent } from './detail/data-forest-detail.component';
import { DataForestUpdateComponent } from './update/data-forest-update.component';
import DataForestResolve from './route/data-forest-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const dataForestRoute: Routes = [
  {
    path: '',
    component: DataForestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DataForestDetailComponent,
    resolve: {
      dataForest: DataForestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DataForestUpdateComponent,
    resolve: {
      dataForest: DataForestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DataForestUpdateComponent,
    resolve: {
      dataForest: DataForestResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default dataForestRoute;
