import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../request-info.test-samples';

import { RequestInfoFormService } from './request-info-form.service';

describe('RequestInfo Form Service', () => {
  let service: RequestInfoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RequestInfoFormService);
  });

  describe('Service methods', () => {
    describe('createRequestInfoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRequestInfoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            contract: expect.any(Object),
            requestDate: expect.any(Object),
            effectiveDateStart: expect.any(Object),
            effectiveDateEnd: expect.any(Object),
            createdAt: expect.any(Object),
            createdBy: expect.any(Object),
            updatedAt: expect.any(Object),
            updatedBy: expect.any(Object),
            requestType: expect.any(Object),
          }),
        );
      });

      it('passing IRequestInfo should create a new form with FormGroup', () => {
        const formGroup = service.createRequestInfoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            contract: expect.any(Object),
            requestDate: expect.any(Object),
            effectiveDateStart: expect.any(Object),
            effectiveDateEnd: expect.any(Object),
            createdAt: expect.any(Object),
            createdBy: expect.any(Object),
            updatedAt: expect.any(Object),
            updatedBy: expect.any(Object),
            requestType: expect.any(Object),
          }),
        );
      });
    });

    describe('getRequestInfo', () => {
      it('should return NewRequestInfo for default RequestInfo initial value', () => {
        const formGroup = service.createRequestInfoFormGroup(sampleWithNewData);

        const requestInfo = service.getRequestInfo(formGroup) as any;

        expect(requestInfo).toMatchObject(sampleWithNewData);
      });

      it('should return NewRequestInfo for empty RequestInfo initial value', () => {
        const formGroup = service.createRequestInfoFormGroup();

        const requestInfo = service.getRequestInfo(formGroup) as any;

        expect(requestInfo).toMatchObject({});
      });

      it('should return IRequestInfo', () => {
        const formGroup = service.createRequestInfoFormGroup(sampleWithRequiredData);

        const requestInfo = service.getRequestInfo(formGroup) as any;

        expect(requestInfo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRequestInfo should not enable id FormControl', () => {
        const formGroup = service.createRequestInfoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRequestInfo should disable id FormControl', () => {
        const formGroup = service.createRequestInfoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
