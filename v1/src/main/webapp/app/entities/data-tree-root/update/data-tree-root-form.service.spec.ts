import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../data-tree-root.test-samples';

import { DataTreeRootFormService } from './data-tree-root-form.service';

describe('DataTreeRoot Form Service', () => {
  let service: DataTreeRootFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DataTreeRootFormService);
  });

  describe('Service methods', () => {
    describe('createDataTreeRootFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDataTreeRootFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            stereoType: expect.any(Object),
            name: expect.any(Object),
            caption: expect.any(Object),
            documentation: expect.any(Object),
            dataTreeBranch: expect.any(Object),
            rootToField: expect.any(Object),
          })
        );
      });

      it('passing IDataTreeRoot should create a new form with FormGroup', () => {
        const formGroup = service.createDataTreeRootFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            stereoType: expect.any(Object),
            name: expect.any(Object),
            caption: expect.any(Object),
            documentation: expect.any(Object),
            dataTreeBranch: expect.any(Object),
            rootToField: expect.any(Object),
          })
        );
      });
    });

    describe('getDataTreeRoot', () => {
      it('should return NewDataTreeRoot for default DataTreeRoot initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDataTreeRootFormGroup(sampleWithNewData);

        const dataTreeRoot = service.getDataTreeRoot(formGroup) as any;

        expect(dataTreeRoot).toMatchObject(sampleWithNewData);
      });

      it('should return NewDataTreeRoot for empty DataTreeRoot initial value', () => {
        const formGroup = service.createDataTreeRootFormGroup();

        const dataTreeRoot = service.getDataTreeRoot(formGroup) as any;

        expect(dataTreeRoot).toMatchObject({});
      });

      it('should return IDataTreeRoot', () => {
        const formGroup = service.createDataTreeRootFormGroup(sampleWithRequiredData);

        const dataTreeRoot = service.getDataTreeRoot(formGroup) as any;

        expect(dataTreeRoot).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDataTreeRoot should not enable id FormControl', () => {
        const formGroup = service.createDataTreeRootFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDataTreeRoot should disable id FormControl', () => {
        const formGroup = service.createDataTreeRootFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
