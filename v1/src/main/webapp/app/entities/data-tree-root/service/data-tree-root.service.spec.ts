import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDataTreeRoot } from '../data-tree-root.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../data-tree-root.test-samples';

import { DataTreeRootService } from './data-tree-root.service';

const requireRestSample: IDataTreeRoot = {
  ...sampleWithRequiredData,
};

describe('DataTreeRoot Service', () => {
  let service: DataTreeRootService;
  let httpMock: HttpTestingController;
  let expectedResult: IDataTreeRoot | IDataTreeRoot[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DataTreeRootService);
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

    it('should create a DataTreeRoot', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const dataTreeRoot = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(dataTreeRoot).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DataTreeRoot', () => {
      const dataTreeRoot = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(dataTreeRoot).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DataTreeRoot', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DataTreeRoot', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DataTreeRoot', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDataTreeRootToCollectionIfMissing', () => {
      it('should add a DataTreeRoot to an empty array', () => {
        const dataTreeRoot: IDataTreeRoot = sampleWithRequiredData;
        expectedResult = service.addDataTreeRootToCollectionIfMissing([], dataTreeRoot);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dataTreeRoot);
      });

      it('should not add a DataTreeRoot to an array that contains it', () => {
        const dataTreeRoot: IDataTreeRoot = sampleWithRequiredData;
        const dataTreeRootCollection: IDataTreeRoot[] = [
          {
            ...dataTreeRoot,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDataTreeRootToCollectionIfMissing(dataTreeRootCollection, dataTreeRoot);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DataTreeRoot to an array that doesn't contain it", () => {
        const dataTreeRoot: IDataTreeRoot = sampleWithRequiredData;
        const dataTreeRootCollection: IDataTreeRoot[] = [sampleWithPartialData];
        expectedResult = service.addDataTreeRootToCollectionIfMissing(dataTreeRootCollection, dataTreeRoot);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dataTreeRoot);
      });

      it('should add only unique DataTreeRoot to an array', () => {
        const dataTreeRootArray: IDataTreeRoot[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const dataTreeRootCollection: IDataTreeRoot[] = [sampleWithRequiredData];
        expectedResult = service.addDataTreeRootToCollectionIfMissing(dataTreeRootCollection, ...dataTreeRootArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dataTreeRoot: IDataTreeRoot = sampleWithRequiredData;
        const dataTreeRoot2: IDataTreeRoot = sampleWithPartialData;
        expectedResult = service.addDataTreeRootToCollectionIfMissing([], dataTreeRoot, dataTreeRoot2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dataTreeRoot);
        expect(expectedResult).toContain(dataTreeRoot2);
      });

      it('should accept null and undefined values', () => {
        const dataTreeRoot: IDataTreeRoot = sampleWithRequiredData;
        expectedResult = service.addDataTreeRootToCollectionIfMissing([], null, dataTreeRoot, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dataTreeRoot);
      });

      it('should return initial array if no DataTreeRoot is added', () => {
        const dataTreeRootCollection: IDataTreeRoot[] = [sampleWithRequiredData];
        expectedResult = service.addDataTreeRootToCollectionIfMissing(dataTreeRootCollection, undefined, null);
        expect(expectedResult).toEqual(dataTreeRootCollection);
      });
    });

    describe('compareDataTreeRoot', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDataTreeRoot(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDataTreeRoot(entity1, entity2);
        const compareResult2 = service.compareDataTreeRoot(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDataTreeRoot(entity1, entity2);
        const compareResult2 = service.compareDataTreeRoot(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDataTreeRoot(entity1, entity2);
        const compareResult2 = service.compareDataTreeRoot(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
