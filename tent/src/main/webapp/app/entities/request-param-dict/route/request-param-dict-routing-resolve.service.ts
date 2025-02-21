import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRequestParamDict } from '../request-param-dict.model';
import { RequestParamDictService } from '../service/request-param-dict.service';

const requestParamDictResolve = (route: ActivatedRouteSnapshot): Observable<null | IRequestParamDict> => {
  const id = route.params['id'];
  if (id) {
    return inject(RequestParamDictService)
      .find(id)
      .pipe(
        mergeMap((requestParamDict: HttpResponse<IRequestParamDict>) => {
          if (requestParamDict.body) {
            return of(requestParamDict.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default requestParamDictResolve;
