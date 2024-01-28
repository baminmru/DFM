import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../data-forest.test-samples';

import { DataForestFormService } from './data-forest-form.service';

describe('DataForest Form Service', () => {
  let service: DataForestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DataForestFormService);
  });

  describe('Service methods', () => {
    describe('createDataForestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDataForestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            caption: expect.any(Object),
            documentation: expect.any(Object),
            forestTrees: expect.any(Object),
          })
        );
      });

      it('passing IDataForest should create a new form with FormGroup', () => {
        const formGroup = service.createDataForestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            caption: expect.any(Object),
            documentation: expect.any(Object),
            forestTrees: expect.any(Object),
          })
        );
      });
    });

    describe('getDataForest', () => {
      it('should return NewDataForest for default DataForest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDataForestFormGroup(sampleWithNewData);

        const dataForest = service.getDataForest(formGroup) as any;

        expect(dataForest).toMatchObject(sampleWithNewData);
      });

      it('should return NewDataForest for empty DataForest initial value', () => {
        const formGroup = service.createDataForestFormGroup();

        const dataForest = service.getDataForest(formGroup) as any;

        expect(dataForest).toMatchObject({});
      });

      it('should return IDataForest', () => {
        const formGroup = service.createDataForestFormGroup(sampleWithRequiredData);

        const dataForest = service.getDataForest(formGroup) as any;

        expect(dataForest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDataForest should not enable id FormControl', () => {
        const formGroup = service.createDataForestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDataForest should disable id FormControl', () => {
        const formGroup = service.createDataForestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
