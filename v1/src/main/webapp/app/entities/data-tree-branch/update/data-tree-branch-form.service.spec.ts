import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../data-tree-branch.test-samples';

import { DataTreeBranchFormService } from './data-tree-branch-form.service';

describe('DataTreeBranch Form Service', () => {
  let service: DataTreeBranchFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DataTreeBranchFormService);
  });

  describe('Service methods', () => {
    describe('createDataTreeBranchFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDataTreeBranchFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            stereoType: expect.any(Object),
            name: expect.any(Object),
            caption: expect.any(Object),
            documentation: expect.any(Object),
            dataTreeLeaf: expect.any(Object),
            branchToField: expect.any(Object),
            branchParent: expect.any(Object),
          })
        );
      });

      it('passing IDataTreeBranch should create a new form with FormGroup', () => {
        const formGroup = service.createDataTreeBranchFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            stereoType: expect.any(Object),
            name: expect.any(Object),
            caption: expect.any(Object),
            documentation: expect.any(Object),
            dataTreeLeaf: expect.any(Object),
            branchToField: expect.any(Object),
            branchParent: expect.any(Object),
          })
        );
      });
    });

    describe('getDataTreeBranch', () => {
      it('should return NewDataTreeBranch for default DataTreeBranch initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDataTreeBranchFormGroup(sampleWithNewData);

        const dataTreeBranch = service.getDataTreeBranch(formGroup) as any;

        expect(dataTreeBranch).toMatchObject(sampleWithNewData);
      });

      it('should return NewDataTreeBranch for empty DataTreeBranch initial value', () => {
        const formGroup = service.createDataTreeBranchFormGroup();

        const dataTreeBranch = service.getDataTreeBranch(formGroup) as any;

        expect(dataTreeBranch).toMatchObject({});
      });

      it('should return IDataTreeBranch', () => {
        const formGroup = service.createDataTreeBranchFormGroup(sampleWithRequiredData);

        const dataTreeBranch = service.getDataTreeBranch(formGroup) as any;

        expect(dataTreeBranch).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDataTreeBranch should not enable id FormControl', () => {
        const formGroup = service.createDataTreeBranchFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDataTreeBranch should disable id FormControl', () => {
        const formGroup = service.createDataTreeBranchFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
