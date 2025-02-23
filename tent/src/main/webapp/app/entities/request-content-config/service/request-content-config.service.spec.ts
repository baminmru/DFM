import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IRequestContentConfig } from '../request-content-config.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../request-content-config.test-samples';

import { RequestContentConfigService, RestRequestContentConfig } from './request-content-config.service';

const requireRestSample: RestRequestContentConfig = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.format(DATE_FORMAT),
  updatedAt: sampleWithRequiredData.updatedAt?.format(DATE_FORMAT),
};

describe('RequestContentConfig Service', () => {
  let service: RequestContentConfigService;
  let httpMock: HttpTestingController;
  let expectedResult: IRequestContentConfig | IRequestContentConfig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(RequestContentConfigService);
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

    it('should create a RequestContentConfig', () => {
      const requestContentConfig = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(requestContentConfig).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RequestContentConfig', () => {
      const requestContentConfig = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(requestContentConfig).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RequestContentConfig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RequestContentConfig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RequestContentConfig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRequestContentConfigToCollectionIfMissing', () => {
      it('should add a RequestContentConfig to an empty array', () => {
        const requestContentConfig: IRequestContentConfig = sampleWithRequiredData;
        expectedResult = service.addRequestContentConfigToCollectionIfMissing([], requestContentConfig);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requestContentConfig);
      });

      it('should not add a RequestContentConfig to an array that contains it', () => {
        const requestContentConfig: IRequestContentConfig = sampleWithRequiredData;
        const requestContentConfigCollection: IRequestContentConfig[] = [
          {
            ...requestContentConfig,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRequestContentConfigToCollectionIfMissing(requestContentConfigCollection, requestContentConfig);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RequestContentConfig to an array that doesn't contain it", () => {
        const requestContentConfig: IRequestContentConfig = sampleWithRequiredData;
        const requestContentConfigCollection: IRequestContentConfig[] = [sampleWithPartialData];
        expectedResult = service.addRequestContentConfigToCollectionIfMissing(requestContentConfigCollection, requestContentConfig);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requestContentConfig);
      });

      it('should add only unique RequestContentConfig to an array', () => {
        const requestContentConfigArray: IRequestContentConfig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const requestContentConfigCollection: IRequestContentConfig[] = [sampleWithRequiredData];
        expectedResult = service.addRequestContentConfigToCollectionIfMissing(requestContentConfigCollection, ...requestContentConfigArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const requestContentConfig: IRequestContentConfig = sampleWithRequiredData;
        const requestContentConfig2: IRequestContentConfig = sampleWithPartialData;
        expectedResult = service.addRequestContentConfigToCollectionIfMissing([], requestContentConfig, requestContentConfig2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requestContentConfig);
        expect(expectedResult).toContain(requestContentConfig2);
      });

      it('should accept null and undefined values', () => {
        const requestContentConfig: IRequestContentConfig = sampleWithRequiredData;
        expectedResult = service.addRequestContentConfigToCollectionIfMissing([], null, requestContentConfig, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requestContentConfig);
      });

      it('should return initial array if no RequestContentConfig is added', () => {
        const requestContentConfigCollection: IRequestContentConfig[] = [sampleWithRequiredData];
        expectedResult = service.addRequestContentConfigToCollectionIfMissing(requestContentConfigCollection, undefined, null);
        expect(expectedResult).toEqual(requestContentConfigCollection);
      });
    });

    describe('compareRequestContentConfig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRequestContentConfig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRequestContentConfig(entity1, entity2);
        const compareResult2 = service.compareRequestContentConfig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRequestContentConfig(entity1, entity2);
        const compareResult2 = service.compareRequestContentConfig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRequestContentConfig(entity1, entity2);
        const compareResult2 = service.compareRequestContentConfig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
