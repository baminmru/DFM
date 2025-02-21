import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRequestConfig, NewRequestConfig } from '../request-config.model';

export type PartialUpdateRequestConfig = Partial<IRequestConfig> & Pick<IRequestConfig, 'id'>;

export type EntityResponseType = HttpResponse<IRequestConfig>;
export type EntityArrayResponseType = HttpResponse<IRequestConfig[]>;

@Injectable({ providedIn: 'root' })
export class RequestConfigService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/request-configs');

  create(requestConfig: NewRequestConfig): Observable<EntityResponseType> {
    return this.http.post<IRequestConfig>(this.resourceUrl, requestConfig, { observe: 'response' });
  }

  update(requestConfig: IRequestConfig): Observable<EntityResponseType> {
    return this.http.put<IRequestConfig>(`${this.resourceUrl}/${this.getRequestConfigIdentifier(requestConfig)}`, requestConfig, {
      observe: 'response',
    });
  }

  partialUpdate(requestConfig: PartialUpdateRequestConfig): Observable<EntityResponseType> {
    return this.http.patch<IRequestConfig>(`${this.resourceUrl}/${this.getRequestConfigIdentifier(requestConfig)}`, requestConfig, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRequestConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRequestConfig[]>(this.resourceUrl, { params: options, observe: 'response' });
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
}
