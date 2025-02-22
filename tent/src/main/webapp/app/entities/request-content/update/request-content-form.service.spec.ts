import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../request-content.test-samples';

import { RequestContentFormService } from './request-content-form.service';

describe('RequestContent Form Service', () => {
  let service: RequestContentFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RequestContentFormService);
  });

  describe('Service methods', () => {
    describe('createRequestContentFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRequestContentFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            paramCode: expect.any(Object),
            paramValue: expect.any(Object),
            requestInfoId: expect.any(Object),
          }),
        );
      });

      it('passing IRequestContent should create a new form with FormGroup', () => {
        const formGroup = service.createRequestContentFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            paramCode: expect.any(Object),
            paramValue: expect.any(Object),
            requestInfoId: expect.any(Object),
          }),
        );
      });
    });

    describe('getRequestContent', () => {
      it('should return NewRequestContent for default RequestContent initial value', () => {
        const formGroup = service.createRequestContentFormGroup(sampleWithNewData);

        const requestContent = service.getRequestContent(formGroup) as any;

        expect(requestContent).toMatchObject(sampleWithNewData);
      });

      it('should return NewRequestContent for empty RequestContent initial value', () => {
        const formGroup = service.createRequestContentFormGroup();

        const requestContent = service.getRequestContent(formGroup) as any;

        expect(requestContent).toMatchObject({});
      });

      it('should return IRequestContent', () => {
        const formGroup = service.createRequestContentFormGroup(sampleWithRequiredData);

        const requestContent = service.getRequestContent(formGroup) as any;

        expect(requestContent).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRequestContent should not enable id FormControl', () => {
        const formGroup = service.createRequestContentFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRequestContent should disable id FormControl', () => {
        const formGroup = service.createRequestContentFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
