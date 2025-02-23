import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRequestContent, NewRequestContent } from '../request-content.model';

export type PartialUpdateRequestContent = Partial<IRequestContent> & Pick<IRequestContent, 'id'>;

type RestOf<T extends IRequestContent | NewRequestContent> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestRequestContent = RestOf<IRequestContent>;

export type NewRestRequestContent = RestOf<NewRequestContent>;

export type PartialUpdateRestRequestContent = RestOf<PartialUpdateRequestContent>;

export type EntityResponseType = HttpResponse<IRequestContent>;
export type EntityArrayResponseType = HttpResponse<IRequestContent[]>;

@Injectable({ providedIn: 'root' })
export class RequestContentService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/request-contents');

  create(requestContent: NewRequestContent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestContent);
    return this.http
      .post<RestRequestContent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(requestContent: IRequestContent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestContent);
    return this.http
      .put<RestRequestContent>(`${this.resourceUrl}/${this.getRequestContentIdentifier(requestContent)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(requestContent: PartialUpdateRequestContent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requestContent);
    return this.http
      .patch<RestRequestContent>(`${this.resourceUrl}/${this.getRequestContentIdentifier(requestContent)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestRequestContent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestRequestContent[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
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

  protected convertDateFromClient<T extends IRequestContent | NewRequestContent | PartialUpdateRequestContent>(
    requestContent: T,
  ): RestOf<T> {
    return {
      ...requestContent,
      createdAt: requestContent.createdAt?.format(DATE_FORMAT) ?? null,
      updatedAt: requestContent.updatedAt?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restRequestContent: RestRequestContent): IRequestContent {
    return {
      ...restRequestContent,
      createdAt: restRequestContent.createdAt ? dayjs(restRequestContent.createdAt) : undefined,
      updatedAt: restRequestContent.updatedAt ? dayjs(restRequestContent.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestRequestContent>): HttpResponse<IRequestContent> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestRequestContent[]>): HttpResponse<IRequestContent[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
