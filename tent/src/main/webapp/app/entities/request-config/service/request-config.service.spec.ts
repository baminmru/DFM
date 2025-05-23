import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IRequestConfig } from '../request-config.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../request-config.test-samples';

import { RequestConfigService, RestRequestConfig } from './request-config.service';

const requireRestSample: RestRequestConfig = {
  ...sampleWithRequiredData,
  effectiveDateStart: sampleWithRequiredData.effectiveDateStart?.format(DATE_FORMAT),
  effectiveDateEnd: sampleWithRequiredData.effectiveDateEnd?.format(DATE_FORMAT),
  createdAt: sampleWithRequiredData.createdAt?.format(DATE_FORMAT),
  updatedAt: sampleWithRequiredData.updatedAt?.format(DATE_FORMAT),
};

describe('RequestConfig Service', () => {
  let service: RequestConfigService;
  let httpMock: HttpTestingController;
  let expectedResult: IRequestConfig | IRequestConfig[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(RequestConfigService);
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

    it('should create a RequestConfig', () => {
      const requestConfig = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(requestConfig).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RequestConfig', () => {
      const requestConfig = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(requestConfig).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RequestConfig', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RequestConfig', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RequestConfig', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRequestConfigToCollectionIfMissing', () => {
      it('should add a RequestConfig to an empty array', () => {
        const requestConfig: IRequestConfig = sampleWithRequiredData;
        expectedResult = service.addRequestConfigToCollectionIfMissing([], requestConfig);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requestConfig);
      });

      it('should not add a RequestConfig to an array that contains it', () => {
        const requestConfig: IRequestConfig = sampleWithRequiredData;
        const requestConfigCollection: IRequestConfig[] = [
          {
            ...requestConfig,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRequestConfigToCollectionIfMissing(requestConfigCollection, requestConfig);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RequestConfig to an array that doesn't contain it", () => {
        const requestConfig: IRequestConfig = sampleWithRequiredData;
        const requestConfigCollection: IRequestConfig[] = [sampleWithPartialData];
        expectedResult = service.addRequestConfigToCollectionIfMissing(requestConfigCollection, requestConfig);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requestConfig);
      });

      it('should add only unique RequestConfig to an array', () => {
        const requestConfigArray: IRequestConfig[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const requestConfigCollection: IRequestConfig[] = [sampleWithRequiredData];
        expectedResult = service.addRequestConfigToCollectionIfMissing(requestConfigCollection, ...requestConfigArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const requestConfig: IRequestConfig = sampleWithRequiredData;
        const requestConfig2: IRequestConfig = sampleWithPartialData;
        expectedResult = service.addRequestConfigToCollectionIfMissing([], requestConfig, requestConfig2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requestConfig);
        expect(expectedResult).toContain(requestConfig2);
      });

      it('should accept null and undefined values', () => {
        const requestConfig: IRequestConfig = sampleWithRequiredData;
        expectedResult = service.addRequestConfigToCollectionIfMissing([], null, requestConfig, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requestConfig);
      });

      it('should return initial array if no RequestConfig is added', () => {
        const requestConfigCollection: IRequestConfig[] = [sampleWithRequiredData];
        expectedResult = service.addRequestConfigToCollectionIfMissing(requestConfigCollection, undefined, null);
        expect(expectedResult).toEqual(requestConfigCollection);
      });
    });

    describe('compareRequestConfig', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRequestConfig(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRequestConfig(entity1, entity2);
        const compareResult2 = service.compareRequestConfig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRequestConfig(entity1, entity2);
        const compareResult2 = service.compareRequestConfig(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRequestConfig(entity1, entity2);
        const compareResult2 = service.compareRequestConfig(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
