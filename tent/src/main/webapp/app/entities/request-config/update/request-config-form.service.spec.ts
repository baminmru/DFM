import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../request-config.test-samples';

import { RequestConfigFormService } from './request-config-form.service';

describe('RequestConfig Form Service', () => {
  let service: RequestConfigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RequestConfigFormService);
  });

  describe('Service methods', () => {
    describe('createRequestConfigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRequestConfigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            version: expect.any(Object),
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

      it('passing IRequestConfig should create a new form with FormGroup', () => {
        const formGroup = service.createRequestConfigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            version: expect.any(Object),
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

    describe('getRequestConfig', () => {
      it('should return NewRequestConfig for default RequestConfig initial value', () => {
        const formGroup = service.createRequestConfigFormGroup(sampleWithNewData);

        const requestConfig = service.getRequestConfig(formGroup) as any;

        expect(requestConfig).toMatchObject(sampleWithNewData);
      });

      it('should return NewRequestConfig for empty RequestConfig initial value', () => {
        const formGroup = service.createRequestConfigFormGroup();

        const requestConfig = service.getRequestConfig(formGroup) as any;

        expect(requestConfig).toMatchObject({});
      });

      it('should return IRequestConfig', () => {
        const formGroup = service.createRequestConfigFormGroup(sampleWithRequiredData);

        const requestConfig = service.getRequestConfig(formGroup) as any;

        expect(requestConfig).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRequestConfig should not enable id FormControl', () => {
        const formGroup = service.createRequestConfigFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRequestConfig should disable id FormControl', () => {
        const formGroup = service.createRequestConfigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
