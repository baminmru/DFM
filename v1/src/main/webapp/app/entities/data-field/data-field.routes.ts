import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DataFieldComponent } from './list/data-field.component';
import { DataFieldDetailComponent } from './detail/data-field-detail.component';
import { DataFieldUpdateComponent } from './update/data-field-update.component';
import DataFieldResolve from './route/data-field-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const dataFieldRoute: Routes = [
  {
    path: '',
    component: DataFieldComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DataFieldDetailComponent,
    resolve: {
      dataField: DataFieldResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DataFieldUpdateComponent,
    resolve: {
      dataField: DataFieldResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DataFieldUpdateComponent,
    resolve: {
      dataField: DataFieldResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default dataFieldRoute;
