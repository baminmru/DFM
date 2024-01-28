import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDataTreeLeaf } from '../data-tree-leaf.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../data-tree-leaf.test-samples';

import { DataTreeLeafService } from './data-tree-leaf.service';

const requireRestSample: IDataTreeLeaf = {
  ...sampleWithRequiredData,
};

describe('DataTreeLeaf Service', () => {
  let service: DataTreeLeafService;
  let httpMock: HttpTestingController;
  let expectedResult: IDataTreeLeaf | IDataTreeLeaf[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DataTreeLeafService);
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

    it('should create a DataTreeLeaf', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const dataTreeLeaf = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(dataTreeLeaf).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DataTreeLeaf', () => {
      const dataTreeLeaf = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(dataTreeLeaf).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DataTreeLeaf', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DataTreeLeaf', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DataTreeLeaf', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDataTreeLeafToCollectionIfMissing', () => {
      it('should add a DataTreeLeaf to an empty array', () => {
        const dataTreeLeaf: IDataTreeLeaf = sampleWithRequiredData;
        expectedResult = service.addDataTreeLeafToCollectionIfMissing([], dataTreeLeaf);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dataTreeLeaf);
      });

      it('should not add a DataTreeLeaf to an array that contains it', () => {
        const dataTreeLeaf: IDataTreeLeaf = sampleWithRequiredData;
        const dataTreeLeafCollection: IDataTreeLeaf[] = [
          {
            ...dataTreeLeaf,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDataTreeLeafToCollectionIfMissing(dataTreeLeafCollection, dataTreeLeaf);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DataTreeLeaf to an array that doesn't contain it", () => {
        const dataTreeLeaf: IDataTreeLeaf = sampleWithRequiredData;
        const dataTreeLeafCollection: IDataTreeLeaf[] = [sampleWithPartialData];
        expectedResult = service.addDataTreeLeafToCollectionIfMissing(dataTreeLeafCollection, dataTreeLeaf);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dataTreeLeaf);
      });

      it('should add only unique DataTreeLeaf to an array', () => {
        const dataTreeLeafArray: IDataTreeLeaf[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const dataTreeLeafCollection: IDataTreeLeaf[] = [sampleWithRequiredData];
        expectedResult = service.addDataTreeLeafToCollectionIfMissing(dataTreeLeafCollection, ...dataTreeLeafArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dataTreeLeaf: IDataTreeLeaf = sampleWithRequiredData;
        const dataTreeLeaf2: IDataTreeLeaf = sampleWithPartialData;
        expectedResult = service.addDataTreeLeafToCollectionIfMissing([], dataTreeLeaf, dataTreeLeaf2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dataTreeLeaf);
        expect(expectedResult).toContain(dataTreeLeaf2);
      });

      it('should accept null and undefined values', () => {
        const dataTreeLeaf: IDataTreeLeaf = sampleWithRequiredData;
        expectedResult = service.addDataTreeLeafToCollectionIfMissing([], null, dataTreeLeaf, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dataTreeLeaf);
      });

      it('should return initial array if no DataTreeLeaf is added', () => {
        const dataTreeLeafCollection: IDataTreeLeaf[] = [sampleWithRequiredData];
        expectedResult = service.addDataTreeLeafToCollectionIfMissing(dataTreeLeafCollection, undefined, null);
        expect(expectedResult).toEqual(dataTreeLeafCollection);
      });
    });

    describe('compareDataTreeLeaf', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDataTreeLeaf(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDataTreeLeaf(entity1, entity2);
        const compareResult2 = service.compareDataTreeLeaf(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDataTreeLeaf(entity1, entity2);
        const compareResult2 = service.compareDataTreeLeaf(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDataTreeLeaf(entity1, entity2);
        const compareResult2 = service.compareDataTreeLeaf(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
