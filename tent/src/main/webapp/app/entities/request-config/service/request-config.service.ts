import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRequestConfig, NewRequestConfig } from '../request-config.model';

export type PartialUpdateRequestConfig = Partial<IRequestConfig> & Pick<IRequestConfig, 'id'>;

type RestOf<T extends IRequestConfig | NewRequestConfig> = Omit<
  T,
  'effectiveDateStart' | 'effectiveDateEnd' | 'createdAt' | 'updatedAt'
> & {
  effectiveDateStart?: string | null;
  effectiveDateEnd?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestRequestConfig = RestOf<IRequestConfig>;

export type NewRestRequestConfig = RestOf<NewRequestConfig>;

export type PartialUpdateRestRequestConfig = RestOf<PartialUpdateRequestConfig>;

export type EntityResponseType = HttpResponse<IRequestConfig>;
export type EntityArrayResponseType = HttpResponse<IRequestConfig[]>;

@Injectable({ providedIn: 'root' })
export class RequestConfigService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/request-configs');

  create(requestConfig: NewRequestConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestConfig);
    return this.http
      .post<RestRequestConfig>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(requestConfig: IRequestConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestConfig);
    return this.http
      .put<RestRequestConfig>(`${this.resourceUrl}/${this.getRequestConfigIdentifier(requestConfig)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(requestConfig: PartialUpdateRequestConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestConfig);
    return this.http
      .patch<RestRequestConfig>(`${this.resourceUrl}/${this.getRequestConfigIdentifier(requestConfig)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestRequestConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRequestConfig[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRequestConfigIdentifier(requestConfig: Pick<IRequestConfig, 'id'>): number {
    return requestConfig.id;
  }

  compareRequestConfig(o1: Pick<IRequestConfig, 'id'> | null, o2: Pick<IRequestConfig, 'id'> | null): boolean {
    return o1 && o2 ? this.getRequestConfigIdentifier(o1) === this.getRequestConfigIdentifier(o2) : o1 === o2;
  }

  addRequestConfigToCollectionIfMissing<Type extends Pick<IRequestConfig, 'id'>>(
    requestConfigCollection: Type[],
    ...requestConfigsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const requestConfigs: Type[] = requestConfigsToCheck.filter(isPresent);
    if (requestConfigs.length > 0) {
      const requestConfigCollectionIdentifiers = requestConfigCollection.map(requestConfigItem =>
        this.getRequestConfigIdentifier(requestConfigItem),
      );
      const requestConfigsToAdd = requestConfigs.filter(requestConfigItem => {
        const requestConfigIdentifier = this.getRequestConfigIdentifier(requestConfigItem);
        if (requestConfigCollectionIdentifiers.includes(requestConfigIdentifier)) {
          return false;
        }
        requestConfigCollectionIdentifiers.push(requestConfigIdentifier);
        return true;
      });
      return [...requestConfigsToAdd, ...requestConfigCollection];
    }
    return requestConfigCollection;
  }

  protected convertDateFromClient<T extends IRequestConfig | NewRequestConfig | PartialUpdateRequestConfig>(requestConfig: T): RestOf<T> {
    return {
      ...requestConfig,
      effectiveDateStart: requestConfig.effectiveDateStart?.format(DATE_FORMAT) ?? null,
      effectiveDateEnd: requestConfig.effectiveDateEnd?.format(DATE_FORMAT) ?? null,
      createdAt: requestConfig.createdAt?.format(DATE_FORMAT) ?? null,
      updatedAt: requestConfig.updatedAt?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restRequestConfig: RestRequestConfig): IRequestConfig {
    return {
      ...restRequestConfig,
      effectiveDateStart: restRequestConfig.effectiveDateStart ? dayjs(restRequestConfig.effectiveDateStart) : undefined,
      effectiveDateEnd: restRequestConfig.effectiveDateEnd ? dayjs(restRequestConfig.effectiveDateEnd) : undefined,
      createdAt: restRequestConfig.createdAt ? dayjs(restRequestConfig.createdAt) : undefined,
      updatedAt: restRequestConfig.updatedAt ? dayjs(restRequestConfig.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRequestConfig>): HttpResponse<IRequestConfig> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRequestConfig[]>): HttpResponse<IRequestConfig[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
