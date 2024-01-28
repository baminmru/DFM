import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDataForest } from '../data-forest.model';
import { DataForestService } from '../service/data-forest.service';

export const dataForestResolve = (route: ActivatedRouteSnapshot): Observable<null | IDataForest> => {
  const id = route.params['id'];
  if (id) {
    return inject(DataForestService)
      .find(id)
      .pipe(
        mergeMap((dataForest: HttpResponse<IDataForest>) => {
          if (dataForest.body) {
            return of(dataForest.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default dataForestResolve;
