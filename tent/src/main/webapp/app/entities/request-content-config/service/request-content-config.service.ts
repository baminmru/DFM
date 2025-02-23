import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRequestContentConfig, NewRequestContentConfig } from '../request-content-config.model';

export type PartialUpdateRequestContentConfig = Partial<IRequestContentConfig> & Pick<IRequestContentConfig, 'id'>;

type RestOf<T extends IRequestContentConfig | NewRequestContentConfig> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestRequestContentConfig = RestOf<IRequestContentConfig>;

export type NewRestRequestContentConfig = RestOf<NewRequestContentConfig>;

export type PartialUpdateRestRequestContentConfig = RestOf<PartialUpdateRequestContentConfig>;

export type EntityResponseType = HttpResponse<IRequestContentConfig>;
export type EntityArrayResponseType = HttpResponse<IRequestContentConfig[]>;

@Injectable({ providedIn: 'root' })
export class RequestContentConfigService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/request-content-configs');

  create(requestContentConfig: NewRequestContentConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestContentConfig);
    return this.http
      .post<RestRequestContentConfig>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(requestContentConfig: IRequestContentConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestContentConfig);
    return this.http
      .put<RestRequestContentConfig>(`${this.resourceUrl}/${this.getRequestContentConfigIdentifier(requestContentConfig)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(requestContentConfig: PartialUpdateRequestContentConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestContentConfig);
    return this.http
      .patch<RestRequestContentConfig>(`${this.resourceUrl}/${this.getRequestContentConfigIdentifier(requestContentConfig)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestRequestContentConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRequestContentConfig[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRequestContentConfigIdentifier(requestContentConfig: Pick<IRequestContentConfig, 'id'>): number {
    return requestContentConfig.id;
  }

  compareRequestContentConfig(o1: Pick<IRequestContentConfig, 'id'> | null, o2: Pick<IRequestContentConfig, 'id'> | null): boolean {
    return o1 && o2 ? this.getRequestContentConfigIdentifier(o1) === this.getRequestContentConfigIdentifier(o2) : o1 === o2;
  }

  addRequestContentConfigToCollectionIfMissing<Type extends Pick<IRequestContentConfig, 'id'>>(
    requestContentConfigCollection: Type[],
    ...requestContentConfigsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const requestContentConfigs: Type[] = requestContentConfigsToCheck.filter(isPresent);
    if (requestContentConfigs.length > 0) {
      const requestContentConfigCollectionIdentifiers = requestContentConfigCollection.map(requestContentConfigItem =>
        this.getRequestContentConfigIdentifier(requestContentConfigItem),
      );
      const requestContentConfigsToAdd = requestContentConfigs.filter(requestContentConfigItem => {
        const requestContentConfigIdentifier = this.getRequestContentConfigIdentifier(requestContentConfigItem);
        if (requestContentConfigCollectionIdentifiers.includes(requestContentConfigIdentifier)) {
          return false;
        }
        requestContentConfigCollectionIdentifiers.push(requestContentConfigIdentifier);
        return true;
      });
      return [...requestContentConfigsToAdd, ...requestContentConfigCollection];
    }
    return requestContentConfigCollection;
  }

  protected convertDateFromClient<T extends IRequestContentConfig | NewRequestContentConfig | PartialUpdateRequestContentConfig>(
    requestContentConfig: T,
  ): RestOf<T> {
    return {
      ...requestContentConfig,
      createdAt: requestContentConfig.createdAt?.format(DATE_FORMAT) ?? null,
      updatedAt: requestContentConfig.updatedAt?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restRequestContentConfig: RestRequestContentConfig): IRequestContentConfig {
    return {
      ...restRequestContentConfig,
      createdAt: restRequestContentConfig.createdAt ? dayjs(restRequestContentConfig.createdAt) : undefined,
      updatedAt: restRequestContentConfig.updatedAt ? dayjs(restRequestContentConfig.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRequestContentConfig>): HttpResponse<IRequestContentConfig> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRequestContentConfig[]>): HttpResponse<IRequestContentConfig[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
