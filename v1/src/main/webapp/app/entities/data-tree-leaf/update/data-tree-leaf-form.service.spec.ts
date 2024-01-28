import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../data-tree-leaf.test-samples';

import { DataTreeLeafFormService } from './data-tree-leaf-form.service';

describe('DataTreeLeaf Form Service', () => {
  let service: DataTreeLeafFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DataTreeLeafFormService);
  });

  describe('Service methods', () => {
    describe('createDataTreeLeafFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDataTreeLeafFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            stereoType: expect.any(Object),
            name: expect.any(Object),
            caption: expect.any(Object),
            documentation: expect.any(Object),
            leafToFields: expect.any(Object),
          })
        );
      });

      it('passing IDataTreeLeaf should create a new form with FormGroup', () => {
        const formGroup = service.createDataTreeLeafFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            stereoType: expect.any(Object),
            name: expect.any(Object),
            caption: expect.any(Object),
            documentation: expect.any(Object),
            leafToFields: expect.any(Object),
          })
        );
      });
    });

    describe('getDataTreeLeaf', () => {
      it('should return NewDataTreeLeaf for default DataTreeLeaf initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDataTreeLeafFormGroup(sampleWithNewData);

        const dataTreeLeaf = service.getDataTreeLeaf(formGroup) as any;

        expect(dataTreeLeaf).toMatchObject(sampleWithNewData);
      });

      it('should return NewDataTreeLeaf for empty DataTreeLeaf initial value', () => {
        const formGroup = service.createDataTreeLeafFormGroup();

        const dataTreeLeaf = service.getDataTreeLeaf(formGroup) as any;

        expect(dataTreeLeaf).toMatchObject({});
      });

      it('should return IDataTreeLeaf', () => {
        const formGroup = service.createDataTreeLeafFormGroup(sampleWithRequiredData);

        const dataTreeLeaf = service.getDataTreeLeaf(formGroup) as any;

        expect(dataTreeLeaf).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDataTreeLeaf should not enable id FormControl', () => {
        const formGroup = service.createDataTreeLeafFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDataTreeLeaf should disable id FormControl', () => {
        const formGroup = service.createDataTreeLeafFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
