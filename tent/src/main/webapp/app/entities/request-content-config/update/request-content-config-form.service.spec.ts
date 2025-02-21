import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../request-content-config.test-samples';

import { RequestContentConfigFormService } from './request-content-config-form.service';

describe('RequestContentConfig Form Service', () => {
  let service: RequestContentConfigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RequestContentConfigFormService);
  });

  describe('Service methods', () => {
    describe('createRequestContentConfigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRequestContentConfigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            requestConfigId: expect.any(Object),
            parameter: expect.any(Object),
            isMandatory: expect.any(Object),
          }),
        );
      });

      it('passing IRequestContentConfig should create a new form with FormGroup', () => {
        const formGroup = service.createRequestContentConfigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            requestConfigId: expect.any(Object),
            parameter: expect.any(Object),
            isMandatory: expect.any(Object),
          }),
        );
      });
    });

    describe('getRequestContentConfig', () => {
      it('should return NewRequestContentConfig for default RequestContentConfig initial value', () => {
        const formGroup = service.createRequestContentConfigFormGroup(sampleWithNewData);

        const requestContentConfig = service.getRequestContentConfig(formGroup) as any;

        expect(requestContentConfig).toMatchObject(sampleWithNewData);
      });

      it('should return NewRequestContentConfig for empty RequestContentConfig initial value', () => {
        const formGroup = service.createRequestContentConfigFormGroup();

        const requestContentConfig = service.getRequestContentConfig(formGroup) as any;

        expect(requestContentConfig).toMatchObject({});
      });

      it('should return IRequestContentConfig', () => {
        const formGroup = service.createRequestContentConfigFormGroup(sampleWithRequiredData);

        const requestContentConfig = service.getRequestContentConfig(formGroup) as any;

        expect(requestContentConfig).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRequestContentConfig should not enable id FormControl', () => {
        const formGroup = service.createRequestContentConfigFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRequestContentConfig should disable id FormControl', () => {
        const formGroup = service.createRequestContentConfigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
