import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDataTreeBranch, NewDataTreeBranch } from '../data-tree-branch.model';

export type PartialUpdateDataTreeBranch = Partial<IDataTreeBranch> & Pick<IDataTreeBranch, 'id'>;

export type EntityResponseType = HttpResponse<IDataTreeBranch>;
export type EntityArrayResponseType = HttpResponse<IDataTreeBranch[]>;

@Injectable({ providedIn: 'root' })
export class DataTreeBranchService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/data-tree-branches');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dataTreeBranch: NewDataTreeBranch): Observable<EntityResponseType> {
    return this.http.post<IDataTreeBranch>(this.resourceUrl, dataTreeBranch, { observe: 'response' });
  }

  update(dataTreeBranch: IDataTreeBranch): Observable<EntityResponseType> {
    return this.http.put<IDataTreeBranch>(`${this.resourceUrl}/${this.getDataTreeBranchIdentifier(dataTreeBranch)}`, dataTreeBranch, {
      observe: 'response',
    });
  }

  partialUpdate(dataTreeBranch: PartialUpdateDataTreeBranch): Observable<EntityResponseType> {
    return this.http.patch<IDataTreeBranch>(`${this.resourceUrl}/${this.getDataTreeBranchIdentifier(dataTreeBranch)}`, dataTreeBranch, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDataTreeBranch>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDataTreeBranch[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDataTreeBranchIdentifier(dataTreeBranch: Pick<IDataTreeBranch, 'id'>): number {
    return dataTreeBranch.id;
  }

  compareDataTreeBranch(o1: Pick<IDataTreeBranch, 'id'> | null, o2: Pick<IDataTreeBranch, 'id'> | null): boolean {
    return o1 && o2 ? this.getDataTreeBranchIdentifier(o1) === this.getDataTreeBranchIdentifier(o2) : o1 === o2;
  }

  addDataTreeBranchToCollectionIfMissing<Type extends Pick<IDataTreeBranch, 'id'>>(
    dataTreeBranchCollection: Type[],
    ...dataTreeBranchesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dataTreeBranches: Type[] = dataTreeBranchesToCheck.filter(isPresent);
    if (dataTreeBranches.length > 0) {
      const dataTreeBranchCollectionIdentifiers = dataTreeBranchCollection.map(
        dataTreeBranchItem => this.getDataTreeBranchIdentifier(dataTreeBranchItem)!
      );
      const dataTreeBranchesToAdd = dataTreeBranches.filter(dataTreeBranchItem => {
        const dataTreeBranchIdentifier = this.getDataTreeBranchIdentifier(dataTreeBranchItem);
        if (dataTreeBranchCollectionIdentifiers.includes(dataTreeBranchIdentifier)) {
          return false;
        }
        dataTreeBranchCollectionIdentifiers.push(dataTreeBranchIdentifier);
        return true;
      });
      return [...dataTreeBranchesToAdd, ...dataTreeBranchCollection];
    }
    return dataTreeBranchCollection;
  }
}
