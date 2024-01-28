import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDataField } from '../data-field.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../data-field.test-samples';

import { DataFieldService } from './data-field.service';

const requireRestSample: IDataField = {
  ...sampleWithRequiredData,
};

describe('DataField Service', () => {
  let service: DataFieldService;
  let httpMock: HttpTestingController;
  let expectedResult: IDataField | IDataField[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DataFieldService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a DataField', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const dataField = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(dataField).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DataField', () => {
      const dataField = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(dataField).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DataField', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DataField', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DataField', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDataFieldToCollectionIfMissing', () => {
      it('should add a DataField to an empty array', () => {
        const dataField: IDataField = sampleWithRequiredData;
        expectedResult = service.addDataFieldToCollectionIfMissing([], dataField);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dataField);
      });

      it('should not add a DataField to an array that contains it', () => {
        const dataField: IDataField = sampleWithRequiredData;
        const dataFieldCollection: IDataField[] = [
          {
            ...dataField,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDataFieldToCollectionIfMissing(dataFieldCollection, dataField);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DataField to an array that doesn't contain it", () => {
        const dataField: IDataField = sampleWithRequiredData;
        const dataFieldCollection: IDataField[] = [sampleWithPartialData];
        expectedResult = service.addDataFieldToCollectionIfMissing(dataFieldCollection, dataField);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dataField);
      });

      it('should add only unique DataField to an array', () => {
        const dataFieldArray: IDataField[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const dataFieldCollection: IDataField[] = [sampleWithRequiredData];
        expectedResult = service.addDataFieldToCollectionIfMissing(dataFieldCollection, ...dataFieldArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dataField: IDataField = sampleWithRequiredData;
        const dataField2: IDataField = sampleWithPartialData;
        expectedResult = service.addDataFieldToCollectionIfMissing([], dataField, dataField2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dataField);
        expect(expectedResult).toContain(dataField2);
      });

      it('should accept null and undefined values', () => {
        const dataField: IDataField = sampleWithRequiredData;
        expectedResult = service.addDataFieldToCollectionIfMissing([], null, dataField, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dataField);
      });

      it('should return initial array if no DataField is added', () => {
        const dataFieldCollection: IDataField[] = [sampleWithRequiredData];
        expectedResult = service.addDataFieldToCollectionIfMissing(dataFieldCollection, undefined, null);
        expect(expectedResult).toEqual(dataFieldCollection);
      });
    });

    describe('compareDataField', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDataField(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDataField(entity1, entity2);
        const compareResult2 = service.compareDataField(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDataField(entity1, entity2);
        const compareResult2 = service.compareDataField(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDataField(entity1, entity2);
        const compareResult2 = service.compareDataField(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
