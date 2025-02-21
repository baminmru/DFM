import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRequestInfo, NewRequestInfo } from '../request-info.model';

export type PartialUpdateRequestInfo = Partial<IRequestInfo> & Pick<IRequestInfo, 'id'>;

type RestOf<T extends IRequestInfo | NewRequestInfo> = Omit<
  T,
  'requestDate' | 'effectiveDateStart' | 'effectiveDateEnd' | 'createdAt' | 'updatedAt'
> & {
  requestDate?: string | null;
  effectiveDateStart?: string | null;
  effectiveDateEnd?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestRequestInfo = RestOf<IRequestInfo>;

export type NewRestRequestInfo = RestOf<NewRequestInfo>;

export type PartialUpdateRestRequestInfo = RestOf<PartialUpdateRequestInfo>;

export type EntityResponseType = HttpResponse<IRequestInfo>;
export type EntityArrayResponseType = HttpResponse<IRequestInfo[]>;

@Injectable({ providedIn: 'root' })
export class RequestInfoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/request-infos');

  create(requestInfo: NewRequestInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestInfo);
    return this.http
      .post<RestRequestInfo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(requestInfo: IRequestInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestInfo);
    return this.http
      .put<RestRequestInfo>(`${this.resourceUrl}/${this.getRequestInfoIdentifier(requestInfo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(requestInfo: PartialUpdateRequestInfo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestInfo);
    return this.http
      .patch<RestRequestInfo>(`${this.resourceUrl}/${this.getRequestInfoIdentifier(requestInfo)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestRequestInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRequestInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRequestInfoIdentifier(requestInfo: Pick<IRequestInfo, 'id'>): number {
    return requestInfo.id;
  }

  compareRequestInfo(o1: Pick<IRequestInfo, 'id'> | null, o2: Pick<IRequestInfo, 'id'> | null): boolean {
    return o1 && o2 ? this.getRequestInfoIdentifier(o1) === this.getRequestInfoIdentifier(o2) : o1 === o2;
  }

  addRequestInfoToCollectionIfMissing<Type extends Pick<IRequestInfo, 'id'>>(
    requestInfoCollection: Type[],
    ...requestInfosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const requestInfos: Type[] = requestInfosToCheck.filter(isPresent);
    if (requestInfos.length > 0) {
      const requestInfoCollectionIdentifiers = requestInfoCollection.map(requestInfoItem => this.getRequestInfoIdentifier(requestInfoItem));
      const requestInfosToAdd = requestInfos.filter(requestInfoItem => {
        const requestInfoIdentifier = this.getRequestInfoIdentifier(requestInfoItem);
        if (requestInfoCollectionIdentifiers.includes(requestInfoIdentifier)) {
          return false;
        }
        requestInfoCollectionIdentifiers.push(requestInfoIdentifier);
        return true;
      });
      return [...requestInfosToAdd, ...requestInfoCollection];
    }
    return requestInfoCollection;
  }

  protected convertDateFromClient<T extends IRequestInfo | NewRequestInfo | PartialUpdateRequestInfo>(requestInfo: T): RestOf<T> {
    return {
      ...requestInfo,
      requestDate: requestInfo.requestDate?.format(DATE_FORMAT) ?? null,
      effectiveDateStart: requestInfo.effectiveDateStart?.format(DATE_FORMAT) ?? null,
      effectiveDateEnd: requestInfo.effectiveDateEnd?.format(DATE_FORMAT) ?? null,
      createdAt: requestInfo.createdAt?.format(DATE_FORMAT) ?? null,
      updatedAt: requestInfo.updatedAt?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restRequestInfo: RestRequestInfo): IRequestInfo {
    return {
      ...restRequestInfo,
      requestDate: restRequestInfo.requestDate ? dayjs(restRequestInfo.requestDate) : undefined,
      effectiveDateStart: restRequestInfo.effectiveDateStart ? dayjs(restRequestInfo.effectiveDateStart) : undefined,
      effectiveDateEnd: restRequestInfo.effectiveDateEnd ? dayjs(restRequestInfo.effectiveDateEnd) : undefined,
      createdAt: restRequestInfo.createdAt ? dayjs(restRequestInfo.createdAt) : undefined,
      updatedAt: restRequestInfo.updatedAt ? dayjs(restRequestInfo.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRequestInfo>): HttpResponse<IRequestInfo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRequestInfo[]>): HttpResponse<IRequestInfo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
