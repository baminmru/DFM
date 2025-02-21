import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRequestInfo } from '../request-info.model';
import { RequestInfoService } from '../service/request-info.service';

const requestInfoResolve = (route: ActivatedRouteSnapshot): Observable<null | IRequestInfo> => {
  const id = route.params['id'];
  if (id) {
    return inject(RequestInfoService)
      .find(id)
      .pipe(
        mergeMap((requestInfo: HttpResponse<IRequestInfo>) => {
          if (requestInfo.body) {
            return of(requestInfo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default requestInfoResolve;
