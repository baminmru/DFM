import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDataTreeBranch, NewDataTreeBranch } from '../data-tree-branch.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDataTreeBranch for edit and NewDataTreeBranchFormGroupInput for create.
 */
type DataTreeBranchFormGroupInput = IDataTreeBranch | PartialWithRequiredKeyOf<NewDataTreeBranch>;

type DataTreeBranchFormDefaults = Pick<NewDataTreeBranch, 'id'>;

type DataTreeBranchFormGroupContent = {
  id: FormControl<IDataTreeBranch['id'] | NewDataTreeBranch['id']>;
  stereoType: FormControl<IDataTreeBranch['stereoType']>;
  name: FormControl<IDataTreeBranch['name']>;
  caption: FormControl<IDataTreeBranch['caption']>;
  documentation: FormControl<IDataTreeBranch['documentation']>;
  dataTreeLeaf: FormControl<IDataTreeBranch['dataTreeLeaf']>;
  branchToField: FormControl<IDataTreeBranch['branchToField']>;
  branchParent: FormControl<IDataTreeBranch['branchParent']>;
};

export type DataTreeBranchFormGroup = FormGroup<DataTreeBranchFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DataTreeBranchFormService {
  createDataTreeBranchFormGroup(dataTreeBranch: DataTreeBranchFormGroupInput = { id: null }): DataTreeBranchFormGroup {
    const dataTreeBranchRawValue = {
      ...this.getFormDefaults(),
      ...dataTreeBranch,
    };
    return new FormGroup<DataTreeBranchFormGroupContent>({
      id: new FormControl(
        { value: dataTreeBranchRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      stereoType: new FormControl(dataTreeBranchRawValue.stereoType, {
        validators: [Validators.required],
      }),
      name: new FormControl(dataTreeBranchRawValue.name, {
        validators: [Validators.required],
      }),
      caption: new FormControl(dataTreeBranchRawValue.caption, {
        validators: [Validators.required],
      }),
      documentation: new FormControl(dataTreeBranchRawValue.documentation),
      dataTreeLeaf: new FormControl(dataTreeBranchRawValue.dataTreeLeaf),
      branchToField: new FormControl(dataTreeBranchRawValue.branchToField),
      branchParent: new FormControl(dataTreeBranchRawValue.branchParent),
    });
  }

  getDataTreeBranch(form: DataTreeBranchFormGroup): IDataTreeBranch | NewDataTreeBranch {
    return form.getRawValue() as IDataTreeBranch | NewDataTreeBranch;
  }

  resetForm(form: DataTreeBranchFormGroup, dataTreeBranch: DataTreeBranchFormGroupInput): void {
    const dataTreeBranchRawValue = { ...this.getFormDefaults(), ...dataTreeBranch };
    form.reset(
      {
        ...dataTreeBranchRawValue,
        id: { value: dataTreeBranchRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DataTreeBranchFormDefaults {
    return {
      id: null,
    };
  }
}
