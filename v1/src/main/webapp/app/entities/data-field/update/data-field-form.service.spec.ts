import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../data-field.test-samples';

import { DataFieldFormService } from './data-field-form.service';

describe('DataField Form Service', () => {
  let service: DataFieldFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DataFieldFormService);
  });

  describe('Service methods', () => {
    describe('createDataFieldFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDataFieldFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            inputType: expect.any(Object),
            fieldType: expect.any(Object),
            sequence: expect.any(Object),
            isBrief: expect.any(Object),
            briefSequence: expect.any(Object),
            allowNull: expect.any(Object),
            name: expect.any(Object),
            caption: expect.any(Object),
            documentation: expect.any(Object),
            tabName: expect.any(Object),
            groupName: expect.any(Object),
            generationStyle: expect.any(Object),
            refToRoot: expect.any(Object),
          })
        );
      });

      it('passing IDataField should create a new form with FormGroup', () => {
        const formGroup = service.createDataFieldFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            inputType: expect.any(Object),
            fieldType: expect.any(Object),
            sequence: expect.any(Object),
            isBrief: expect.any(Object),
            briefSequence: expect.any(Object),
            allowNull: expect.any(Object),
            name: expect.any(Object),
            caption: expect.any(Object),
            documentation: expect.any(Object),
            tabName: expect.any(Object),
            groupName: expect.any(Object),
            generationStyle: expect.any(Object),
            refToRoot: expect.any(Object),
          })
        );
      });
    });

    describe('getDataField', () => {
      it('should return NewDataField for default DataField initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDataFieldFormGroup(sampleWithNewData);

        const dataField = service.getDataField(formGroup) as any;

        expect(dataField).toMatchObject(sampleWithNewData);
      });

      it('should return NewDataField for empty DataField initial value', () => {
        const formGroup = service.createDataFieldFormGroup();

        const dataField = service.getDataField(formGroup) as any;

        expect(dataField).toMatchObject({});
      });

      it('should return IDataField', () => {
        const formGroup = service.createDataFieldFormGroup(sampleWithRequiredData);

        const dataField = service.getDataField(formGroup) as any;

        expect(dataField).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDataField should not enable id FormControl', () => {
        const formGroup = service.createDataFieldFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDataField should disable id FormControl', () => {
        const formGroup = service.createDataFieldFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
