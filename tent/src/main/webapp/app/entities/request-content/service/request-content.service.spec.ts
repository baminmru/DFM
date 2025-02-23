import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IRequestContent } from '../request-content.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../request-content.test-samples';

import { RequestContentService, RestRequestContent } from './request-content.service';

const requireRestSample: RestRequestContent = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.format(DATE_FORMAT),
  updatedAt: sampleWithRequiredData.updatedAt?.format(DATE_FORMAT),
};

describe('RequestContent Service', () => {
  let service: RequestContentService;
  let httpMock: HttpTestingController;
  let expectedResult: IRequestContent | IRequestContent[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(RequestContentService);
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

    it('should create a RequestContent', () => {
      const requestContent = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(requestContent).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RequestContent', () => {
      const requestContent = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(requestContent).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RequestContent', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RequestContent', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RequestContent', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRequestContentToCollectionIfMissing', () => {
      it('should add a RequestContent to an empty array', () => {
        const requestContent: IRequestContent = sampleWithRequiredData;
        expectedResult = service.addRequestContentToCollectionIfMissing([], requestContent);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requestContent);
      });

      it('should not add a RequestContent to an array that contains it', () => {
        const requestContent: IRequestContent = sampleWithRequiredData;
        const requestContentCollection: IRequestContent[] = [
          {
            ...requestContent,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRequestContentToCollectionIfMissing(requestContentCollection, requestContent);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RequestContent to an array that doesn't contain it", () => {
        const requestContent: IRequestContent = sampleWithRequiredData;
        const requestContentCollection: IRequestContent[] = [sampleWithPartialData];
        expectedResult = service.addRequestContentToCollectionIfMissing(requestContentCollection, requestContent);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requestContent);
      });

      it('should add only unique RequestContent to an array', () => {
        const requestContentArray: IRequestContent[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const requestContentCollection: IRequestContent[] = [sampleWithRequiredData];
        expectedResult = service.addRequestContentToCollectionIfMissing(requestContentCollection, ...requestContentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const requestContent: IRequestContent = sampleWithRequiredData;
        const requestContent2: IRequestContent = sampleWithPartialData;
        expectedResult = service.addRequestContentToCollectionIfMissing([], requestContent, requestContent2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(requestContent);
        expect(expectedResult).toContain(requestContent2);
      });

      it('should accept null and undefined values', () => {
        const requestContent: IRequestContent = sampleWithRequiredData;
        expectedResult = service.addRequestContentToCollectionIfMissing([], null, requestContent, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(requestContent);
      });

      it('should return initial array if no RequestContent is added', () => {
        const requestContentCollection: IRequestContent[] = [sampleWithRequiredData];
        expectedResult = service.addRequestContentToCollectionIfMissing(requestContentCollection, undefined, null);
        expect(expectedResult).toEqual(requestContentCollection);
      });
    });

    describe('compareRequestContent', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRequestContent(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRequestContent(entity1, entity2);
        const compareResult2 = service.compareRequestContent(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRequestContent(entity1, entity2);
        const compareResult2 = service.compareRequestContent(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRequestContent(entity1, entity2);
        const compareResult2 = service.compareRequestContent(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
