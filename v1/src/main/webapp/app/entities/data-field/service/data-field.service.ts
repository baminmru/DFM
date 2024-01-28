import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDataField, NewDataField } from '../data-field.model';

export type PartialUpdateDataField = Partial<IDataField> & Pick<IDataField, 'id'>;

export type EntityResponseType = HttpResponse<IDataField>;
export type EntityArrayResponseType = HttpResponse<IDataField[]>;

@Injectable({ providedIn: 'root' })
export class DataFieldService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/data-fields');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(dataField: NewDataField): Observable<EntityResponseType> {
    return this.http.post<IDataField>(this.resourceUrl, dataField, { observe: 'response' });
  }

  update(dataField: IDataField): Observable<EntityResponseType> {
    return this.http.put<IDataField>(`${this.resourceUrl}/${this.getDataFieldIdentifier(dataField)}`, dataField, { observe: 'response' });
  }

  partialUpdate(dataField: PartialUpdateDataField): Observable<EntityResponseType> {
    return this.http.patch<IDataField>(`${this.resourceUrl}/${this.getDataFieldIdentifier(dataField)}`, dataField, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDataField>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDataField[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDataFieldIdentifier(dataField: Pick<IDataField, 'id'>): number {
    return dataField.id;
  }

  compareDataField(o1: Pick<IDataField, 'id'> | null, o2: Pick<IDataField, 'id'> | null): boolean {
    return o1 && o2 ? this.getDataFieldIdentifier(o1) === this.getDataFieldIdentifier(o2) : o1 === o2;
  }

  addDataFieldToCollectionIfMissing<Type extends Pick<IDataField, 'id'>>(
    dataFieldCollection: Type[],
    ...dataFieldsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dataFields: Type[] = dataFieldsToCheck.filter(isPresent);
    if (dataFields.length > 0) {
      const dataFieldCollectionIdentifiers = dataFieldCollection.map(dataFieldItem => this.getDataFieldIdentifier(dataFieldItem)!);
      const dataFieldsToAdd = dataFields.filter(dataFieldItem => {
        const dataFieldIdentifier = this.getDataFieldIdentifier(dataFieldItem);
        if (dataFieldCollectionIdentifiers.includes(dataFieldIdentifier)) {
          return false;
        }
        dataFieldCollectionIdentifiers.push(dataFieldIdentifier);
        return true;
      });
      return [...dataFieldsToAdd, ...dataFieldCollection];
    }
    return dataFieldCollection;
  }
}
