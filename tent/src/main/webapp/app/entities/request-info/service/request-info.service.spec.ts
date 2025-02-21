import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IRequestInfo } from '../request-info.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../request-info.test-samples';

import { RequestInfoService, RestRequestInfo } from './request-info.service';

const requireRestSample: RestRequestInfo = {
  ...sampleWithRequiredData,
  requestDate: sampleWithRequiredData.requestDate?.format(DATE_FORMAT),
  effectiveDateStart: sampleWithRequiredData.effectiveDateStart?.format(DATE_FORMAT),
  effectiveDateEnd: sampleWithRequiredData.effectiveDateEnd?.format(DATE_FORMAT),
  createdAt: sampleWithRequiredData.createdAt?.format(DATE_FORMAT),
  updatedAt: sampleWithRequiredData.updatedAt?.format(DATE_FORMAT),
};

describe('RequestInfo Service', () => {
  let service: RequestInfoService;
  let httpMock: HttpTestingController;
  let expectedResult: IRequestInfo | IRequestInfo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(RequestInfoService);
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

    it('should create a RequestInfo', () => {
      const requestInfo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(requestInfo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RequestInfo', () => {
      const requestInfo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(requestInfo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RequestInfo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RequestInfo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RequestInfo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRequestInfoToCollectionIfMissing', () => {
      it('should add a RequestInfo to an empty array', () => {
        const requestInfo: IRequestInfo = sampleWithRequiredData;
        expectedResult = service.addRequestInfoToCollectionIfMissing([], requestInfo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requestInfo);
      });

      it('should not add a RequestInfo to an array that contains it', () => {
        const requestInfo: IRequestInfo = sampleWithRequiredData;
        const requestInfoCollection: IRequestInfo[] = [
          {
            ...requestInfo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRequestInfoToCollectionIfMissing(requestInfoCollection, requestInfo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RequestInfo to an array that doesn't contain it", () => {
        const requestInfo: IRequestInfo = sampleWithRequiredData;
        const requestInfoCollection: IRequestInfo[] = [sampleWithPartialData];
        expectedResult = service.addRequestInfoToCollectionIfMissing(requestInfoCollection, requestInfo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requestInfo);
      });

      it('should add only unique RequestInfo to an array', () => {
        const requestInfoArray: IRequestInfo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const requestInfoCollection: IRequestInfo[] = [sampleWithRequiredData];
        expectedResult = service.addRequestInfoToCollectionIfMissing(requestInfoCollection, ...requestInfoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const requestInfo: IRequestInfo = sampleWithRequiredData;
        const requestInfo2: IRequestInfo = sampleWithPartialData;
        expectedResult = service.addRequestInfoToCollectionIfMissing([], requestInfo, requestInfo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requestInfo);
        expect(expectedResult).toContain(requestInfo2);
      });

      it('should accept null and undefined values', () => {
        const requestInfo: IRequestInfo = sampleWithRequiredData;
        expectedResult = service.addRequestInfoToCollectionIfMissing([], null, requestInfo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requestInfo);
      });

      it('should return initial array if no RequestInfo is added', () => {
        const requestInfoCollection: IRequestInfo[] = [sampleWithRequiredData];
        expectedResult = service.addRequestInfoToCollectionIfMissing(requestInfoCollection, undefined, null);
        expect(expectedResult).toEqual(requestInfoCollection);
      });
    });

    describe('compareRequestInfo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRequestInfo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRequestInfo(entity1, entity2);
        const compareResult2 = service.compareRequestInfo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRequestInfo(entity1, entity2);
        const compareResult2 = service.compareRequestInfo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRequestInfo(entity1, entity2);
        const compareResult2 = service.compareRequestInfo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
