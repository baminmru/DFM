import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDataTreeLeaf, NewDataTreeLeaf } from '../data-tree-leaf.model';

export type PartialUpdateDataTreeLeaf = Partial<IDataTreeLeaf> & Pick<IDataTreeLeaf, 'id'>;

export type EntityResponseType = HttpResponse<IDataTreeLeaf>;
export type EntityArrayResponseType = HttpResponse<IDataTreeLeaf[]>;

@Injectable({ providedIn: 'root' })
export class DataTreeLeafService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/data-tree-leaves');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dataTreeLeaf: NewDataTreeLeaf): Observable<EntityResponseType> {
    return this.http.post<IDataTreeLeaf>(this.resourceUrl, dataTreeLeaf, { observe: 'response' });
  }

  update(dataTreeLeaf: IDataTreeLeaf): Observable<EntityResponseType> {
    return this.http.put<IDataTreeLeaf>(`${this.resourceUrl}/${this.getDataTreeLeafIdentifier(dataTreeLeaf)}`, dataTreeLeaf, {
      observe: 'response',
    });
  }

  partialUpdate(dataTreeLeaf: PartialUpdateDataTreeLeaf): Observable<EntityResponseType> {
    return this.http.patch<IDataTreeLeaf>(`${this.resourceUrl}/${this.getDataTreeLeafIdentifier(dataTreeLeaf)}`, dataTreeLeaf, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDataTreeLeaf>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDataTreeLeaf[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDataTreeLeafIdentifier(dataTreeLeaf: Pick<IDataTreeLeaf, 'id'>): number {
    return dataTreeLeaf.id;
  }

  compareDataTreeLeaf(o1: Pick<IDataTreeLeaf, 'id'> | null, o2: Pick<IDataTreeLeaf, 'id'> | null): boolean {
    return o1 && o2 ? this.getDataTreeLeafIdentifier(o1) === this.getDataTreeLeafIdentifier(o2) : o1 === o2;
  }

  addDataTreeLeafToCollectionIfMissing<Type extends Pick<IDataTreeLeaf, 'id'>>(
    dataTreeLeafCollection: Type[],
    ...dataTreeLeavesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dataTreeLeaves: Type[] = dataTreeLeavesToCheck.filter(isPresent);
    if (dataTreeLeaves.length > 0) {
      const dataTreeLeafCollectionIdentifiers = dataTreeLeafCollection.map(
        dataTreeLeafItem => this.getDataTreeLeafIdentifier(dataTreeLeafItem)!
      );
      const dataTreeLeavesToAdd = dataTreeLeaves.filter(dataTreeLeafItem => {
        const dataTreeLeafIdentifier = this.getDataTreeLeafIdentifier(dataTreeLeafItem);
        if (dataTreeLeafCollectionIdentifiers.includes(dataTreeLeafIdentifier)) {
          return false;
        }
        dataTreeLeafCollectionIdentifiers.push(dataTreeLeafIdentifier);
        return true;
      });
      return [...dataTreeLeavesToAdd, ...dataTreeLeafCollection];
    }
    return dataTreeLeafCollection;
  }
}
