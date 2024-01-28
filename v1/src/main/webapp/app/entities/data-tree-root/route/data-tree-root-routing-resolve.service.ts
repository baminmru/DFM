import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDataTreeRoot } from '../data-tree-root.model';
import { DataTreeRootService } from '../service/data-tree-root.service';

export const dataTreeRootResolve = (route: ActivatedRouteSnapshot): Observable<null | IDataTreeRoot> => {
  const id = route.params['id'];
  if (id) {
    return inject(DataTreeRootService)
      .find(id)
      .pipe(
        mergeMap((dataTreeRoot: HttpResponse<IDataTreeRoot>) => {
          if (dataTreeRoot.body) {
            return of(dataTreeRoot.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default dataTreeRootResolve;
