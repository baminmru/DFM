import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRequestType, NewRequestType } from '../request-type.model';

export type PartialUpdateRequestType = Partial<IRequestType> & Pick<IRequestType, 'id'>;

type RestOf<T extends IRequestType | NewRequestType> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestRequestType = RestOf<IRequestType>;

export type NewRestRequestType = RestOf<NewRequestType>;

export type PartialUpdateRestRequestType = RestOf<PartialUpdateRequestType>;

export type EntityResponseType = HttpResponse<IRequestType>;
export type EntityArrayResponseType = HttpResponse<IRequestType[]>;

@Injectable({ providedIn: 'root' })
export class RequestTypeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/request-types');

  create(requestType: NewRequestType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestType);
    return this.http
      .post<RestRequestType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(requestType: IRequestType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestType);
    return this.http
      .put<RestRequestType>(`${this.resourceUrl}/${this.getRequestTypeIdentifier(requestType)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(requestType: PartialUpdateRequestType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestType);
    return this.http
      .patch<RestRequestType>(`${this.resourceUrl}/${this.getRequestTypeIdentifier(requestType)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestRequestType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRequestType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRequestTypeIdentifier(requestType: Pick<IRequestType, 'id'>): number {
    return requestType.id;
  }

  compareRequestType(o1: Pick<IRequestType, 'id'> | null, o2: Pick<IRequestType, 'id'> | null): boolean {
    return o1 && o2 ? this.getRequestTypeIdentifier(o1) === this.getRequestTypeIdentifier(o2) : o1 === o2;
  }

  addRequestTypeToCollectionIfMissing<Type extends Pick<IRequestType, 'id'>>(
    requestTypeCollection: Type[],
    ...requestTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const requestTypes: Type[] = requestTypesToCheck.filter(isPresent);
    if (requestTypes.length > 0) {
      const requestTypeCollectionIdentifiers = requestTypeCollection.map(requestTypeItem => this.getRequestTypeIdentifier(requestTypeItem));
      const requestTypesToAdd = requestTypes.filter(requestTypeItem => {
        const requestTypeIdentifier = this.getRequestTypeIdentifier(requestTypeItem);
        if (requestTypeCollectionIdentifiers.includes(requestTypeIdentifier)) {
          return false;
        }
        requestTypeCollectionIdentifiers.push(requestTypeIdentifier);
        return true;
      });
      return [...requestTypesToAdd, ...requestTypeCollection];
    }
    return requestTypeCollection;
  }

  protected convertDateFromClient<T extends IRequestType | NewRequestType | PartialUpdateRequestType>(requestType: T): RestOf<T> {
    return {
      ...requestType,
      createdAt: requestType.createdAt?.format(DATE_FORMAT) ?? null,
      updatedAt: requestType.updatedAt?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restRequestType: RestRequestType): IRequestType {
    return {
      ...restRequestType,
      createdAt: restRequestType.createdAt ? dayjs(restRequestType.createdAt) : undefined,
      updatedAt: restRequestType.updatedAt ? dayjs(restRequestType.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRequestType>): HttpResponse<IRequestType> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRequestType[]>): HttpResponse<IRequestType[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
