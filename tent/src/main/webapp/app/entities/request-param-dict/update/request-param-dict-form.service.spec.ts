import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../request-param-dict.test-samples';

import { RequestParamDictFormService } from './request-param-dict-form.service';

describe('RequestParamDict Form Service', () => {
  let service: RequestParamDictFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RequestParamDictFormService);
  });

  describe('Service methods', () => {
    describe('createRequestParamDictFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRequestParamDictFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            paramtype: expect.any(Object),
            valueArray: expect.any(Object),
            referenceTo: expect.any(Object),
            requestContentConfig: expect.any(Object),
          }),
        );
      });

      it('passing IRequestParamDict should create a new form with FormGroup', () => {
        const formGroup = service.createRequestParamDictFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            paramtype: expect.any(Object),
            valueArray: expect.any(Object),
            referenceTo: expect.any(Object),
            requestContentConfig: expect.any(Object),
          }),
        );
      });
    });

    describe('getRequestParamDict', () => {
      it('should return NewRequestParamDict for default RequestParamDict initial value', () => {
        const formGroup = service.createRequestParamDictFormGroup(sampleWithNewData);

        const requestParamDict = service.getRequestParamDict(formGroup) as any;

        expect(requestParamDict).toMatchObject(sampleWithNewData);
      });

      it('should return NewRequestParamDict for empty RequestParamDict initial value', () => {
        const formGroup = service.createRequestParamDictFormGroup();

        const requestParamDict = service.getRequestParamDict(formGroup) as any;

        expect(requestParamDict).toMatchObject({});
      });

      it('should return IRequestParamDict', () => {
        const formGroup = service.createRequestParamDictFormGroup(sampleWithRequiredData);

        const requestParamDict = service.getRequestParamDict(formGroup) as any;

        expect(requestParamDict).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRequestParamDict should not enable id FormControl', () => {
        const formGroup = service.createRequestParamDictFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRequestParamDict should disable id FormControl', () => {
        const formGroup = service.createRequestParamDictFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
