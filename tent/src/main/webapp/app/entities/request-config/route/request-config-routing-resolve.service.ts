import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRequestConfig } from '../request-config.model';
import { RequestConfigService } from '../service/request-config.service';

const requestConfigResolve = (route: ActivatedRouteSnapshot): Observable<null | IRequestConfig> => {
  const id = route.params['id'];
  if (id) {
    return inject(RequestConfigService)
      .find(id)
      .pipe(
        mergeMap((requestConfig: HttpResponse<IRequestConfig>) => {
          if (requestConfig.body) {
            return of(requestConfig.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default requestConfigResolve;
