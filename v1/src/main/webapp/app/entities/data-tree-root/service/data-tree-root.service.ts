import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDataTreeRoot, NewDataTreeRoot } from '../data-tree-root.model';

export type PartialUpdateDataTreeRoot = Partial<IDataTreeRoot> & Pick<IDataTreeRoot, 'id'>;

export type EntityResponseType = HttpResponse<IDataTreeRoot>;
export type EntityArrayResponseType = HttpResponse<IDataTreeRoot[]>;

@Injectable({ providedIn: 'root' })
export class DataTreeRootService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/data-tree-roots');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dataTreeRoot: NewDataTreeRoot): Observable<EntityResponseType> {
    return this.http.post<IDataTreeRoot>(this.resourceUrl, dataTreeRoot, { observe: 'response' });
  }

  update(dataTreeRoot: IDataTreeRoot): Observable<EntityResponseType> {
    return this.http.put<IDataTreeRoot>(`${this.resourceUrl}/${this.getDataTreeRootIdentifier(dataTreeRoot)}`, dataTreeRoot, {
      observe: 'response',
    });
  }

  partialUpdate(dataTreeRoot: PartialUpdateDataTreeRoot): Observable<EntityResponseType> {
    return this.http.patch<IDataTreeRoot>(`${this.resourceUrl}/${this.getDataTreeRootIdentifier(dataTreeRoot)}`, dataTreeRoot, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDataTreeRoot>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDataTreeRoot[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDataTreeRootIdentifier(dataTreeRoot: Pick<IDataTreeRoot, 'id'>): number {
    return dataTreeRoot.id;
  }

  compareDataTreeRoot(o1: Pick<IDataTreeRoot, 'id'> | null, o2: Pick<IDataTreeRoot, 'id'> | null): boolean {
    return o1 && o2 ? this.getDataTreeRootIdentifier(o1) === this.getDataTreeRootIdentifier(o2) : o1 === o2;
  }

  addDataTreeRootToCollectionIfMissing<Type extends Pick<IDataTreeRoot, 'id'>>(
    dataTreeRootCollection: Type[],
    ...dataTreeRootsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dataTreeRoots: Type[] = dataTreeRootsToCheck.filter(isPresent);
    if (dataTreeRoots.length > 0) {
      const dataTreeRootCollectionIdentifiers = dataTreeRootCollection.map(
        dataTreeRootItem => this.getDataTreeRootIdentifier(dataTreeRootItem)!
      );
      const dataTreeRootsToAdd = dataTreeRoots.filter(dataTreeRootItem => {
        const dataTreeRootIdentifier = this.getDataTreeRootIdentifier(dataTreeRootItem);
        if (dataTreeRootCollectionIdentifiers.includes(dataTreeRootIdentifier)) {
          return false;
        }
        dataTreeRootCollectionIdentifiers.push(dataTreeRootIdentifier);
        return true;
      });
      return [...dataTreeRootsToAdd, ...dataTreeRootCollection];
    }
    return dataTreeRootCollection;
  }
}
