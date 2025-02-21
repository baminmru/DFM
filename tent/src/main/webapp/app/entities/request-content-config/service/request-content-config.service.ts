import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRequestContentConfig, NewRequestContentConfig } from '../request-content-config.model';

export type PartialUpdateRequestContentConfig = Partial<IRequestContentConfig> & Pick<IRequestContentConfig, 'id'>;

export type EntityResponseType = HttpResponse<IRequestContentConfig>;
export type EntityArrayResponseType = HttpResponse<IRequestContentConfig[]>;

@Injectable({ providedIn: 'root' })
export class RequestContentConfigService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/request-content-configs');

  create(requestContentConfig: NewRequestContentConfig): Observable<EntityResponseType> {
    return this.http.post<IRequestContentConfig>(this.resourceUrl, requestContentConfig, { observe: 'response' });
  }

  update(requestContentConfig: IRequestContentConfig): Observable<EntityResponseType> {
    return this.http.put<IRequestContentConfig>(
      `${this.resourceUrl}/${this.getRequestContentConfigIdentifier(requestContentConfig)}`,
      requestContentConfig,
      { observe: 'response' },
    );
  }

  partialUpdate(requestContentConfig: PartialUpdateRequestContentConfig): Observable<EntityResponseType> {
    return this.http.patch<IRequestContentConfig>(
      `${this.resourceUrl}/${this.getRequestContentConfigIdentifier(requestContentConfig)}`,
      requestContentConfig,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRequestContentConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRequestContentConfig[]>(this.resourceUrl, { params: options, observe: 'response' });
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
}
