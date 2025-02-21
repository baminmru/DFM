import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRequestType } from '../request-type.model';
import { RequestTypeService } from '../service/request-type.service';

const requestTypeResolve = (route: ActivatedRouteSnapshot): Observable<null | IRequestType> => {
  const id = route.params['id'];
  if (id) {
    return inject(RequestTypeService)
      .find(id)
      .pipe(
        mergeMap((requestType: HttpResponse<IRequestType>) => {
          if (requestType.body) {
            return of(requestType.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default requestTypeResolve;
