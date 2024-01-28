import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDataForest } from '../data-forest.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../data-forest.test-samples';

import { DataForestService } from './data-forest.service';

const requireRestSample: IDataForest = {
  ...sampleWithRequiredData,
};

describe('DataForest Service', () => {
  let service: DataForestService;
  let httpMock: HttpTestingController;
  let expectedResult: IDataForest | IDataForest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DataForestService);
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

    it('should create a DataForest', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const dataForest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(dataForest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DataForest', () => {
      const dataForest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(dataForest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DataForest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DataForest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DataForest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDataForestToCollectionIfMissing', () => {
      it('should add a DataForest to an empty array', () => {
        const dataForest: IDataForest = sampleWithRequiredData;
        expectedResult = service.addDataForestToCollectionIfMissing([], dataForest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dataForest);
      });

      it('should not add a DataForest to an array that contains it', () => {
        const dataForest: IDataForest = sampleWithRequiredData;
        const dataForestCollection: IDataForest[] = [
          {
            ...dataForest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDataForestToCollectionIfMissing(dataForestCollection, dataForest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DataForest to an array that doesn't contain it", () => {
        const dataForest: IDataForest = sampleWithRequiredData;
        const dataForestCollection: IDataForest[] = [sampleWithPartialData];
        expectedResult = service.addDataForestToCollectionIfMissing(dataForestCollection, dataForest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dataForest);
      });

      it('should add only unique DataForest to an array', () => {
        const dataForestArray: IDataForest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const dataForestCollection: IDataForest[] = [sampleWithRequiredData];
        expectedResult = service.addDataForestToCollectionIfMissing(dataForestCollection, ...dataForestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dataForest: IDataForest = sampleWithRequiredData;
        const dataForest2: IDataForest = sampleWithPartialData;
        expectedResult = service.addDataForestToCollectionIfMissing([], dataForest, dataForest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dataForest);
        expect(expectedResult).toContain(dataForest2);
      });

      it('should accept null and undefined values', () => {
        const dataForest: IDataForest = sampleWithRequiredData;
        expectedResult = service.addDataForestToCollectionIfMissing([], null, dataForest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dataForest);
      });

      it('should return initial array if no DataForest is added', () => {
        const dataForestCollection: IDataForest[] = [sampleWithRequiredData];
        expectedResult = service.addDataForestToCollectionIfMissing(dataForestCollection, undefined, null);
        expect(expectedResult).toEqual(dataForestCollection);
      });
    });

    describe('compareDataForest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDataForest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDataForest(entity1, entity2);
        const compareResult2 = service.compareDataForest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDataForest(entity1, entity2);
        const compareResult2 = service.compareDataForest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDataForest(entity1, entity2);
        const compareResult2 = service.compareDataForest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
