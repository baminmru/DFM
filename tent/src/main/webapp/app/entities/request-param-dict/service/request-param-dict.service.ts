import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRequestParamDict, NewRequestParamDict } from '../request-param-dict.model';

export type PartialUpdateRequestParamDict = Partial<IRequestParamDict> & Pick<IRequestParamDict, 'id'>;

type RestOf<T extends IRequestParamDict | NewRequestParamDict> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestRequestParamDict = RestOf<IRequestParamDict>;

export type NewRestRequestParamDict = RestOf<NewRequestParamDict>;

export type PartialUpdateRestRequestParamDict = RestOf<PartialUpdateRequestParamDict>;

export type EntityResponseType = HttpResponse<IRequestParamDict>;
export type EntityArrayResponseType = HttpResponse<IRequestParamDict[]>;

@Injectable({ providedIn: 'root' })
export class RequestParamDictService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/request-param-dicts');

  create(requestParamDict: NewRequestParamDict): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestParamDict);
    return this.http
      .post<RestRequestParamDict>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(requestParamDict: IRequestParamDict): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestParamDict);
    return this.http
      .put<RestRequestParamDict>(`${this.resourceUrl}/${this.getRequestParamDictIdentifier(requestParamDict)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(requestParamDict: PartialUpdateRequestParamDict): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestParamDict);
    return this.http
      .patch<RestRequestParamDict>(`${this.resourceUrl}/${this.getRequestParamDictIdentifier(requestParamDict)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestRequestParamDict>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRequestParamDict[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
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

  protected convertDateFromClient<T extends IRequestParamDict | NewRequestParamDict | PartialUpdateRequestParamDict>(
    requestParamDict: T,
  ): RestOf<T> {
    return {
      ...requestParamDict,
      createdAt: requestParamDict.createdAt?.format(DATE_FORMAT) ?? null,
      updatedAt: requestParamDict.updatedAt?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restRequestParamDict: RestRequestParamDict): IRequestParamDict {
    return {
      ...restRequestParamDict,
      createdAt: restRequestParamDict.createdAt ? dayjs(restRequestParamDict.createdAt) : undefined,
      updatedAt: restRequestParamDict.updatedAt ? dayjs(restRequestParamDict.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRequestParamDict>): HttpResponse<IRequestParamDict> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRequestParamDict[]>): HttpResponse<IRequestParamDict[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
