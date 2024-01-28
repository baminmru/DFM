import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDataTreeRoot, NewDataTreeRoot } from '../data-tree-root.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDataTreeRoot for edit and NewDataTreeRootFormGroupInput for create.
 */
type DataTreeRootFormGroupInput = IDataTreeRoot | PartialWithRequiredKeyOf<NewDataTreeRoot>;

type DataTreeRootFormDefaults = Pick<NewDataTreeRoot, 'id' | 'rootToFields'>;

type DataTreeRootFormGroupContent = {
  id: FormControl<IDataTreeRoot['id'] | NewDataTreeRoot['id']>;
  stereoType: FormControl<IDataTreeRoot['stereoType']>;
  name: FormControl<IDataTreeRoot['name']>;
  caption: FormControl<IDataTreeRoot['caption']>;
  documentation: FormControl<IDataTreeRoot['documentation']>;
  dataTreeBranch: FormControl<IDataTreeRoot['dataTreeBranch']>;
  rootToFields: FormControl<IDataTreeRoot['rootToFields']>;
};

export type DataTreeRootFormGroup = FormGroup<DataTreeRootFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DataTreeRootFormService {
  createDataTreeRootFormGroup(dataTreeRoot: DataTreeRootFormGroupInput = { id: null }): DataTreeRootFormGroup {
    const dataTreeRootRawValue = {
      ...this.getFormDefaults(),
      ...dataTreeRoot,
    };
    return new FormGroup<DataTreeRootFormGroupContent>({
      id: new FormControl(
        { value: dataTreeRootRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      stereoType: new FormControl(dataTreeRootRawValue.stereoType),
      name: new FormControl(dataTreeRootRawValue.name),
      caption: new FormControl(dataTreeRootRawValue.caption),
      documentation: new FormControl(dataTreeRootRawValue.documentation),
      dataTreeBranch: new FormControl(dataTreeRootRawValue.dataTreeBranch),
      rootToFields: new FormControl(dataTreeRootRawValue.rootToFields ?? []),
    });
  }

  getDataTreeRoot(form: DataTreeRootFormGroup): IDataTreeRoot | NewDataTreeRoot {
    return form.getRawValue() as IDataTreeRoot | NewDataTreeRoot;
  }

  resetForm(form: DataTreeRootFormGroup, dataTreeRoot: DataTreeRootFormGroupInput): void {
    const dataTreeRootRawValue = { ...this.getFormDefaults(), ...dataTreeRoot };
    form.reset(
      {
        ...dataTreeRootRawValue,
        id: { value: dataTreeRootRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DataTreeRootFormDefaults {
    return {
      id: null,
      rootToFields: [],
    };
  }
}
