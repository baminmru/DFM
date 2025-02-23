import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../source-system.test-samples';

import { SourceSystemFormService } from './source-system-form.service';

describe('SourceSystem Form Service', () => {
  let service: SourceSystemFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SourceSystemFormService);
  });

  describe('Service methods', () => {
    describe('createSourceSystemFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSourceSystemFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            createdAt: expect.any(Object),
            createdBy: expect.any(Object),
            updatedAt: expect.any(Object),
            updatedBy: expect.any(Object),
          }),
        );
      });

      it('passing ISourceSystem should create a new form with FormGroup', () => {
        const formGroup = service.createSourceSystemFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            name: expect.any(Object),
            createdAt: expect.any(Object),
            createdBy: expect.any(Object),
            updatedAt: expect.any(Object),
            updatedBy: expect.any(Object),
          }),
        );
      });
    });

    describe('getSourceSystem', () => {
      it('should return NewSourceSystem for default SourceSystem initial value', () => {
        const formGroup = service.createSourceSystemFormGroup(sampleWithNewData);

        const sourceSystem = service.getSourceSystem(formGroup) as any;

        expect(sourceSystem).toMatchObject(sampleWithNewData);
      });

      it('should return NewSourceSystem for empty SourceSystem initial value', () => {
        const formGroup = service.createSourceSystemFormGroup();

        const sourceSystem = service.getSourceSystem(formGroup) as any;

        expect(sourceSystem).toMatchObject({});
      });

      it('should return ISourceSystem', () => {
        const formGroup = service.createSourceSystemFormGroup(sampleWithRequiredData);

        const sourceSystem = service.getSourceSystem(formGroup) as any;

        expect(sourceSystem).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISourceSystem should not enable id FormControl', () => {
        const formGroup = service.createSourceSystemFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSourceSystem should disable id FormControl', () => {
        const formGroup = service.createSourceSystemFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
