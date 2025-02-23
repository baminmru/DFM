import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISourceSystem } from '../source-system.model';
import { SourceSystemService } from '../service/source-system.service';

const sourceSystemResolve = (route: ActivatedRouteSnapshot): Observable<null | ISourceSystem> => {
  const id = route.params['id'];
  if (id) {
    return inject(SourceSystemService)
      .find(id)
      .pipe(
        mergeMap((sourceSystem: HttpResponse<ISourceSystem>) => {
          if (sourceSystem.body) {
            return of(sourceSystem.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default sourceSystemResolve;
