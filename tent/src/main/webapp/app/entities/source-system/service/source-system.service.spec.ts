import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ISourceSystem } from '../source-system.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../source-system.test-samples';

import { SourceSystemService, RestSourceSystem } from './source-system.service';

const requireRestSample: RestSourceSystem = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.format(DATE_FORMAT),
  updatedAt: sampleWithRequiredData.updatedAt?.format(DATE_FORMAT),
};

describe('SourceSystem Service', () => {
  let service: SourceSystemService;
  let httpMock: HttpTestingController;
  let expectedResult: ISourceSystem | ISourceSystem[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SourceSystemService);
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

    it('should create a SourceSystem', () => {
      const sourceSystem = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(sourceSystem).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SourceSystem', () => {
      const sourceSystem = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(sourceSystem).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SourceSystem', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SourceSystem', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SourceSystem', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSourceSystemToCollectionIfMissing', () => {
      it('should add a SourceSystem to an empty array', () => {
        const sourceSystem: ISourceSystem = sampleWithRequiredData;
        expectedResult = service.addSourceSystemToCollectionIfMissing([], sourceSystem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sourceSystem);
      });

      it('should not add a SourceSystem to an array that contains it', () => {
        const sourceSystem: ISourceSystem = sampleWithRequiredData;
        const sourceSystemCollection: ISourceSystem[] = [
          {
            ...sourceSystem,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSourceSystemToCollectionIfMissing(sourceSystemCollection, sourceSystem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SourceSystem to an array that doesn't contain it", () => {
        const sourceSystem: ISourceSystem = sampleWithRequiredData;
        const sourceSystemCollection: ISourceSystem[] = [sampleWithPartialData];
        expectedResult = service.addSourceSystemToCollectionIfMissing(sourceSystemCollection, sourceSystem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sourceSystem);
      });

      it('should add only unique SourceSystem to an array', () => {
        const sourceSystemArray: ISourceSystem[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const sourceSystemCollection: ISourceSystem[] = [sampleWithRequiredData];
        expectedResult = service.addSourceSystemToCollectionIfMissing(sourceSystemCollection, ...sourceSystemArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const sourceSystem: ISourceSystem = sampleWithRequiredData;
        const sourceSystem2: ISourceSystem = sampleWithPartialData;
        expectedResult = service.addSourceSystemToCollectionIfMissing([], sourceSystem, sourceSystem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(sourceSystem);
        expect(expectedResult).toContain(sourceSystem2);
      });

      it('should accept null and undefined values', () => {
        const sourceSystem: ISourceSystem = sampleWithRequiredData;
        expectedResult = service.addSourceSystemToCollectionIfMissing([], null, sourceSystem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(sourceSystem);
      });

      it('should return initial array if no SourceSystem is added', () => {
        const sourceSystemCollection: ISourceSystem[] = [sampleWithRequiredData];
        expectedResult = service.addSourceSystemToCollectionIfMissing(sourceSystemCollection, undefined, null);
        expect(expectedResult).toEqual(sourceSystemCollection);
      });
    });

    describe('compareSourceSystem', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSourceSystem(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSourceSystem(entity1, entity2);
        const compareResult2 = service.compareSourceSystem(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSourceSystem(entity1, entity2);
        const compareResult2 = service.compareSourceSystem(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSourceSystem(entity1, entity2);
        const compareResult2 = service.compareSourceSystem(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
