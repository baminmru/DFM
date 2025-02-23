import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IRequestParamDict } from '../request-param-dict.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../request-param-dict.test-samples';

import { RequestParamDictService, RestRequestParamDict } from './request-param-dict.service';

const requireRestSample: RestRequestParamDict = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.format(DATE_FORMAT),
  updatedAt: sampleWithRequiredData.updatedAt?.format(DATE_FORMAT),
};

describe('RequestParamDict Service', () => {
  let service: RequestParamDictService;
  let httpMock: HttpTestingController;
  let expectedResult: IRequestParamDict | IRequestParamDict[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(RequestParamDictService);
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

    it('should create a RequestParamDict', () => {
      const requestParamDict = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(requestParamDict).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RequestParamDict', () => {
      const requestParamDict = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(requestParamDict).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RequestParamDict', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RequestParamDict', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RequestParamDict', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRequestParamDictToCollectionIfMissing', () => {
      it('should add a RequestParamDict to an empty array', () => {
        const requestParamDict: IRequestParamDict = sampleWithRequiredData;
        expectedResult = service.addRequestParamDictToCollectionIfMissing([], requestParamDict);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requestParamDict);
      });

      it('should not add a RequestParamDict to an array that contains it', () => {
        const requestParamDict: IRequestParamDict = sampleWithRequiredData;
        const requestParamDictCollection: IRequestParamDict[] = [
          {
            ...requestParamDict,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRequestParamDictToCollectionIfMissing(requestParamDictCollection, requestParamDict);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RequestParamDict to an array that doesn't contain it", () => {
        const requestParamDict: IRequestParamDict = sampleWithRequiredData;
        const requestParamDictCollection: IRequestParamDict[] = [sampleWithPartialData];
        expectedResult = service.addRequestParamDictToCollectionIfMissing(requestParamDictCollection, requestParamDict);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requestParamDict);
      });

      it('should add only unique RequestParamDict to an array', () => {
        const requestParamDictArray: IRequestParamDict[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const requestParamDictCollection: IRequestParamDict[] = [sampleWithRequiredData];
        expectedResult = service.addRequestParamDictToCollectionIfMissing(requestParamDictCollection, ...requestParamDictArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const requestParamDict: IRequestParamDict = sampleWithRequiredData;
        const requestParamDict2: IRequestParamDict = sampleWithPartialData;
        expectedResult = service.addRequestParamDictToCollectionIfMissing([], requestParamDict, requestParamDict2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requestParamDict);
        expect(expectedResult).toContain(requestParamDict2);
      });

      it('should accept null and undefined values', () => {
        const requestParamDict: IRequestParamDict = sampleWithRequiredData;
        expectedResult = service.addRequestParamDictToCollectionIfMissing([], null, requestParamDict, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requestParamDict);
      });

      it('should return initial array if no RequestParamDict is added', () => {
        const requestParamDictCollection: IRequestParamDict[] = [sampleWithRequiredData];
        expectedResult = service.addRequestParamDictToCollectionIfMissing(requestParamDictCollection, undefined, null);
        expect(expectedResult).toEqual(requestParamDictCollection);
      });
    });

    describe('compareRequestParamDict', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRequestParamDict(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRequestParamDict(entity1, entity2);
        const compareResult2 = service.compareRequestParamDict(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRequestParamDict(entity1, entity2);
        const compareResult2 = service.compareRequestParamDict(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRequestParamDict(entity1, entity2);
        const compareResult2 = service.compareRequestParamDict(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
