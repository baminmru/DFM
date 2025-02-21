import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRequestType, NewRequestType } from '../request-type.model';

export type PartialUpdateRequestType = Partial<IRequestType> & Pick<IRequestType, 'id'>;

export type EntityResponseType = HttpResponse<IRequestType>;
export type EntityArrayResponseType = HttpResponse<IRequestType[]>;

@Injectable({ providedIn: 'root' })
export class RequestTypeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/request-types');

  create(requestType: NewRequestType): Observable<EntityResponseType> {
    return this.http.post<IRequestType>(this.resourceUrl, requestType, { observe: 'response' });
  }

  update(requestType: IRequestType): Observable<EntityResponseType> {
    return this.http.put<IRequestType>(`${this.resourceUrl}/${this.getRequestTypeIdentifier(requestType)}`, requestType, {
      observe: 'response',
    });
  }

  partialUpdate(requestType: PartialUpdateRequestType): Observable<EntityResponseType> {
    return this.http.patch<IRequestType>(`${this.resourceUrl}/${this.getRequestTypeIdentifier(requestType)}`, requestType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRequestType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRequestType[]>(this.resourceUrl, { params: options, observe: 'response' });
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
}
