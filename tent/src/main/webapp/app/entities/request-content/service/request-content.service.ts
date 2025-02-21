import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRequestContent, NewRequestContent } from '../request-content.model';

export type PartialUpdateRequestContent = Partial<IRequestContent> & Pick<IRequestContent, 'id'>;

export type EntityResponseType = HttpResponse<IRequestContent>;
export type EntityArrayResponseType = HttpResponse<IRequestContent[]>;

@Injectable({ providedIn: 'root' })
export class RequestContentService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/request-contents');

  create(requestContent: NewRequestContent): Observable<EntityResponseType> {
    return this.http.post<IRequestContent>(this.resourceUrl, requestContent, { observe: 'response' });
  }

  update(requestContent: IRequestContent): Observable<EntityResponseType> {
    return this.http.put<IRequestContent>(`${this.resourceUrl}/${this.getRequestContentIdentifier(requestContent)}`, requestContent, {
      observe: 'response',
    });
  }

  partialUpdate(requestContent: PartialUpdateRequestContent): Observable<EntityResponseType> {
    return this.http.patch<IRequestContent>(`${this.resourceUrl}/${this.getRequestContentIdentifier(requestContent)}`, requestContent, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRequestContent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRequestContent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRequestContentIdentifier(requestContent: Pick<IRequestContent, 'id'>): number {
    return requestContent.id;
  }

  compareRequestContent(o1: Pick<IRequestContent, 'id'> | null, o2: Pick<IRequestContent, 'id'> | null): boolean {
    return o1 && o2 ? this.getRequestContentIdentifier(o1) === this.getRequestContentIdentifier(o2) : o1 === o2;
  }

  addRequestContentToCollectionIfMissing<Type extends Pick<IRequestContent, 'id'>>(
    requestContentCollection: Type[],
    ...requestContentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const requestContents: Type[] = requestContentsToCheck.filter(isPresent);
    if (requestContents.length > 0) {
      const requestContentCollectionIdentifiers = requestContentCollection.map(requestContentItem =>
        this.getRequestContentIdentifier(requestContentItem),
      );
      const requestContentsToAdd = requestContents.filter(requestContentItem => {
        const requestContentIdentifier = this.getRequestContentIdentifier(requestContentItem);
        if (requestContentCollectionIdentifiers.includes(requestContentIdentifier)) {
          return false;
        }
        requestContentCollectionIdentifiers.push(requestContentIdentifier);
        return true;
      });
      return [...requestContentsToAdd, ...requestContentCollection];
    }
    return requestContentCollection;
  }
}
