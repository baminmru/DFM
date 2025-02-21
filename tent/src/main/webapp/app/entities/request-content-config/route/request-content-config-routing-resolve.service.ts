import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRequestContentConfig } from '../request-content-config.model';
import { RequestContentConfigService } from '../service/request-content-config.service';

const requestContentConfigResolve = (route: ActivatedRouteSnapshot): Observable<null | IRequestContentConfig> => {
  const id = route.params['id'];
  if (id) {
    return inject(RequestContentConfigService)
      .find(id)
      .pipe(
        mergeMap((requestContentConfig: HttpResponse<IRequestContentConfig>) => {
          if (requestContentConfig.body) {
            return of(requestContentConfig.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default requestContentConfigResolve;
