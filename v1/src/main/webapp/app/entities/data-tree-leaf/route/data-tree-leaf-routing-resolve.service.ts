import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDataTreeLeaf } from '../data-tree-leaf.model';
import { DataTreeLeafService } from '../service/data-tree-leaf.service';

export const dataTreeLeafResolve = (route: ActivatedRouteSnapshot): Observable<null | IDataTreeLeaf> => {
  const id = route.params['id'];
  if (id) {
    return inject(DataTreeLeafService)
      .find(id)
      .pipe(
        mergeMap((dataTreeLeaf: HttpResponse<IDataTreeLeaf>) => {
          if (dataTreeLeaf.body) {
            return of(dataTreeLeaf.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default dataTreeLeafResolve;
