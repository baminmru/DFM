import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDataForest, NewDataForest } from '../data-forest.model';

export type PartialUpdateDataForest = Partial<IDataForest> & Pick<IDataForest, 'id'>;

export type EntityResponseType = HttpResponse<IDataForest>;
export type EntityArrayResponseType = HttpResponse<IDataForest[]>;

@Injectable({ providedIn: 'root' })
export class DataForestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/data-forests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dataForest: NewDataForest): Observable<EntityResponseType> {
    return this.http.post<IDataForest>(this.resourceUrl, dataForest, { observe: 'response' });
  }

  update(dataForest: IDataForest): Observable<EntityResponseType> {
    return this.http.put<IDataForest>(`${this.resourceUrl}/${this.getDataForestIdentifier(dataForest)}`, dataForest, {
      observe: 'response',
    });
  }

  partialUpdate(dataForest: PartialUpdateDataForest): Observable<EntityResponseType> {
    return this.http.patch<IDataForest>(`${this.resourceUrl}/${this.getDataForestIdentifier(dataForest)}`, dataForest, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDataForest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDataForest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDataForestIdentifier(dataForest: Pick<IDataForest, 'id'>): number {
    return dataForest.id;
  }

  compareDataForest(o1: Pick<IDataForest, 'id'> | null, o2: Pick<IDataForest, 'id'> | null): boolean {
    return o1 && o2 ? this.getDataForestIdentifier(o1) === this.getDataForestIdentifier(o2) : o1 === o2;
  }

  addDataForestToCollectionIfMissing<Type extends Pick<IDataForest, 'id'>>(
    dataForestCollection: Type[],
    ...dataForestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dataForests: Type[] = dataForestsToCheck.filter(isPresent);
    if (dataForests.length > 0) {
      const dataForestCollectionIdentifiers = dataForestCollection.map(dataForestItem => this.getDataForestIdentifier(dataForestItem)!);
      const dataForestsToAdd = dataForests.filter(dataForestItem => {
        const dataForestIdentifier = this.getDataForestIdentifier(dataForestItem);
        if (dataForestCollectionIdentifiers.includes(dataForestIdentifier)) {
          return false;
        }
        dataForestCollectionIdentifiers.push(dataForestIdentifier);
        return true;
      });
      return [...dataForestsToAdd, ...dataForestCollection];
    }
    return dataForestCollection;
  }
}
