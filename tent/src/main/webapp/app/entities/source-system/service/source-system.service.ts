import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISourceSystem, NewSourceSystem } from '../source-system.model';

export type PartialUpdateSourceSystem = Partial<ISourceSystem> & Pick<ISourceSystem, 'id'>;

type RestOf<T extends ISourceSystem | NewSourceSystem> = Omit<T, 'createdAt' | 'updatedAt'> & {
  createdAt?: string | null;
  updatedAt?: string | null;
};

export type RestSourceSystem = RestOf<ISourceSystem>;

export type NewRestSourceSystem = RestOf<NewSourceSystem>;

export type PartialUpdateRestSourceSystem = RestOf<PartialUpdateSourceSystem>;

export type EntityResponseType = HttpResponse<ISourceSystem>;
export type EntityArrayResponseType = HttpResponse<ISourceSystem[]>;

@Injectable({ providedIn: 'root' })
export class SourceSystemService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/source-systems');

  create(sourceSystem: NewSourceSystem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sourceSystem);
    return this.http
      .post<RestSourceSystem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(sourceSystem: ISourceSystem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sourceSystem);
    return this.http
      .put<RestSourceSystem>(`${this.resourceUrl}/${this.getSourceSystemIdentifier(sourceSystem)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(sourceSystem: PartialUpdateSourceSystem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sourceSystem);
    return this.http
      .patch<RestSourceSystem>(`${this.resourceUrl}/${this.getSourceSystemIdentifier(sourceSystem)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestSourceSystem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestSourceSystem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSourceSystemIdentifier(sourceSystem: Pick<ISourceSystem, 'id'>): number {
    return sourceSystem.id;
  }

  compareSourceSystem(o1: Pick<ISourceSystem, 'id'> | null, o2: Pick<ISourceSystem, 'id'> | null): boolean {
    return o1 && o2 ? this.getSourceSystemIdentifier(o1) === this.getSourceSystemIdentifier(o2) : o1 === o2;
  }

  addSourceSystemToCollectionIfMissing<Type extends Pick<ISourceSystem, 'id'>>(
    sourceSystemCollection: Type[],
    ...sourceSystemsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const sourceSystems: Type[] = sourceSystemsToCheck.filter(isPresent);
    if (sourceSystems.length > 0) {
      const sourceSystemCollectionIdentifiers = sourceSystemCollection.map(sourceSystemItem =>
        this.getSourceSystemIdentifier(sourceSystemItem),
      );
      const sourceSystemsToAdd = sourceSystems.filter(sourceSystemItem => {
        const sourceSystemIdentifier = this.getSourceSystemIdentifier(sourceSystemItem);
        if (sourceSystemCollectionIdentifiers.includes(sourceSystemIdentifier)) {
          return false;
        }
        sourceSystemCollectionIdentifiers.push(sourceSystemIdentifier);
        return true;
      });
      return [...sourceSystemsToAdd, ...sourceSystemCollection];
    }
    return sourceSystemCollection;
  }

  protected convertDateFromClient<T extends ISourceSystem | NewSourceSystem | PartialUpdateSourceSystem>(sourceSystem: T): RestOf<T> {
    return {
      ...sourceSystem,
      createdAt: sourceSystem.createdAt?.format(DATE_FORMAT) ?? null,
      updatedAt: sourceSystem.updatedAt?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restSourceSystem: RestSourceSystem): ISourceSystem {
    return {
      ...restSourceSystem,
      createdAt: restSourceSystem.createdAt ? dayjs(restSourceSystem.createdAt) : undefined,
      updatedAt: restSourceSystem.updatedAt ? dayjs(restSourceSystem.updatedAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestSourceSystem>): HttpResponse<ISourceSystem> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestSourceSystem[]>): HttpResponse<ISourceSystem[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
