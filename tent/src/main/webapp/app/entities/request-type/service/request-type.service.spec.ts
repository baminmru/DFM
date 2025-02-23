import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IRequestType } from '../request-type.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../request-type.test-samples';

import { RequestTypeService, RestRequestType } from './request-type.service';

const requireRestSample: RestRequestType = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.format(DATE_FORMAT),
  updatedAt: sampleWithRequiredData.updatedAt?.format(DATE_FORMAT),
};

describe('RequestType Service', () => {
  let service: RequestTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: IRequestType | IRequestType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(RequestTypeService);
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

    it('should create a RequestType', () => {
      const requestType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(requestType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RequestType', () => {
      const requestType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(requestType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RequestType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RequestType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RequestType', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRequestTypeToCollectionIfMissing', () => {
      it('should add a RequestType to an empty array', () => {
        const requestType: IRequestType = sampleWithRequiredData;
        expectedResult = service.addRequestTypeToCollectionIfMissing([], requestType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requestType);
      });

      it('should not add a RequestType to an array that contains it', () => {
        const requestType: IRequestType = sampleWithRequiredData;
        const requestTypeCollection: IRequestType[] = [
          {
            ...requestType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRequestTypeToCollectionIfMissing(requestTypeCollection, requestType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RequestType to an array that doesn't contain it", () => {
        const requestType: IRequestType = sampleWithRequiredData;
        const requestTypeCollection: IRequestType[] = [sampleWithPartialData];
        expectedResult = service.addRequestTypeToCollectionIfMissing(requestTypeCollection, requestType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requestType);
      });

      it('should add only unique RequestType to an array', () => {
        const requestTypeArray: IRequestType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const requestTypeCollection: IRequestType[] = [sampleWithRequiredData];
        expectedResult = service.addRequestTypeToCollectionIfMissing(requestTypeCollection, ...requestTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const requestType: IRequestType = sampleWithRequiredData;
        const requestType2: IRequestType = sampleWithPartialData;
        expectedResult = service.addRequestTypeToCollectionIfMissing([], requestType, requestType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requestType);
        expect(expectedResult).toContain(requestType2);
      });

      it('should accept null and undefined values', () => {
        const requestType: IRequestType = sampleWithRequiredData;
        expectedResult = service.addRequestTypeToCollectionIfMissing([], null, requestType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requestType);
      });

      it('should return initial array if no RequestType is added', () => {
        const requestTypeCollection: IRequestType[] = [sampleWithRequiredData];
        expectedResult = service.addRequestTypeToCollectionIfMissing(requestTypeCollection, undefined, null);
        expect(expectedResult).toEqual(requestTypeCollection);
      });
    });

    describe('compareRequestType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRequestType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRequestType(entity1, entity2);
        const compareResult2 = service.compareRequestType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRequestType(entity1, entity2);
        const compareResult2 = service.compareRequestType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRequestType(entity1, entity2);
        const compareResult2 = service.compareRequestType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
