import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRequestParamDict, NewRequestParamDict } from '../request-param-dict.model';

export type PartialUpdateRequestParamDict = Partial<IRequestParamDict> & Pick<IRequestParamDict, 'id'>;

export type EntityResponseType = HttpResponse<IRequestParamDict>;
export type EntityArrayResponseType = HttpResponse<IRequestParamDict[]>;

@Injectable({ providedIn: 'root' })
export class RequestParamDictService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/request-param-dicts');

  create(requestParamDict: NewRequestParamDict): Observable<EntityResponseType> {
    return this.http.post<IRequestParamDict>(this.resourceUrl, requestParamDict, { observe: 'response' });
  }

  update(requestParamDict: IRequestParamDict): Observable<EntityResponseType> {
    return this.http.put<IRequestParamDict>(
      `${this.resourceUrl}/${this.getRequestParamDictIdentifier(requestParamDict)}`,
      requestParamDict,
      { observe: 'response' },
    );
  }

  partialUpdate(requestParamDict: PartialUpdateRequestParamDict): Observable<EntityResponseType> {
    return this.http.patch<IRequestParamDict>(
      `${this.resourceUrl}/${this.getRequestParamDictIdentifier(requestParamDict)}`,
      requestParamDict,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRequestParamDict>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRequestParamDict[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRequestParamDictIdentifier(requestParamDict: Pick<IRequestParamDict, 'id'>): number {
    return requestParamDict.id;
  }

  compareRequestParamDict(o1: Pick<IRequestParamDict, 'id'> | null, o2: Pick<IRequestParamDict, 'id'> | null): boolean {
    return o1 && o2 ? this.getRequestParamDictIdentifier(o1) === this.getRequestParamDictIdentifier(o2) : o1 === o2;
  }

  addRequestParamDictToCollectionIfMissing<Type extends Pick<IRequestParamDict, 'id'>>(
    requestParamDictCollection: Type[],
    ...requestParamDictsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const requestParamDicts: Type[] = requestParamDictsToCheck.filter(isPresent);
    if (requestParamDicts.length > 0) {
      const requestParamDictCollectionIdentifiers = requestParamDictCollection.map(requestParamDictItem =>
        this.getRequestParamDictIdentifier(requestParamDictItem),
      );
      const requestParamDictsToAdd = requestParamDicts.filter(requestParamDictItem => {
        const requestParamDictIdentifier = this.getRequestParamDictIdentifier(requestParamDictItem);
        if (requestParamDictCollectionIdentifiers.includes(requestParamDictIdentifier)) {
          return false;
        }
        requestParamDictCollectionIdentifiers.push(requestParamDictIdentifier);
        return true;
      });
      return [...requestParamDictsToAdd, ...requestParamDictCollection];
    }
    return requestParamDictCollection;
  }
}
