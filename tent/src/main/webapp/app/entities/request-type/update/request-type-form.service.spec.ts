import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../request-type.test-samples';

import { RequestTypeFormService } from './request-type-form.service';

describe('RequestType Form Service', () => {
  let service: RequestTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RequestTypeFormService);
  });

  describe('Service methods', () => {
    describe('createRequestTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRequestTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });

      it('passing IRequestType should create a new form with FormGroup', () => {
        const formGroup = service.createRequestTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });
    });

    describe('getRequestType', () => {
      it('should return NewRequestType for default RequestType initial value', () => {
        const formGroup = service.createRequestTypeFormGroup(sampleWithNewData);

        const requestType = service.getRequestType(formGroup) as any;

        expect(requestType).toMatchObject(sampleWithNewData);
      });

      it('should return NewRequestType for empty RequestType initial value', () => {
        const formGroup = service.createRequestTypeFormGroup();

        const requestType = service.getRequestType(formGroup) as any;

        expect(requestType).toMatchObject({});
      });

      it('should return IRequestType', () => {
        const formGroup = service.createRequestTypeFormGroup(sampleWithRequiredData);

        const requestType = service.getRequestType(formGroup) as any;

        expect(requestType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRequestType should not enable id FormControl', () => {
        const formGroup = service.createRequestTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRequestType should disable id FormControl', () => {
        const formGroup = service.createRequestTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
