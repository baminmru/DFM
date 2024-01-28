import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDataField } from '../data-field.model';
import { DataFieldService } from '../service/data-field.service';

export const dataFieldResolve = (route: ActivatedRouteSnapshot): Observable<null | IDataField> => {
  const id = route.params['id'];
  if (id) {
    return inject(DataFieldService)
      .find(id)
      .pipe(
        mergeMap((dataField: HttpResponse<IDataField>) => {
          if (dataField.body) {
            return of(dataField.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        })
      );
  }
  return of(null);
};

export default dataFieldResolve;
