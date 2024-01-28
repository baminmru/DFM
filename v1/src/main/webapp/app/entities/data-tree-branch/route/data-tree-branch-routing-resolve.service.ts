import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDataTreeBranch } from '../data-tree-branch.model';
import { DataTreeBranchService } from '../service/data-tree-branch.service';

export const dataTreeBranchResolve = (route: ActivatedRouteSnapshot): Observable<null | IDataTreeBranch> => {
  const id = route.params['id'];
  if (id) {
    return inject(DataTreeBranchService)
      .find(id)
      .pipe(
        mergeMap((dataTreeBranch: HttpResponse<IDataTreeBranch>) => {
          if (dataTreeBranch.body) {
            return of(dataTreeBranch.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default dataTreeBranchResolve;
