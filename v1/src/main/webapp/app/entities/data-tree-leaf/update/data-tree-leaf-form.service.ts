import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDataTreeLeaf, NewDataTreeLeaf } from '../data-tree-leaf.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDataTreeLeaf for edit and NewDataTreeLeafFormGroupInput for create.
 */
type DataTreeLeafFormGroupInput = IDataTreeLeaf | PartialWithRequiredKeyOf<NewDataTreeLeaf>;

type DataTreeLeafFormDefaults = Pick<NewDataTreeLeaf, 'id'>;

type DataTreeLeafFormGroupContent = {
  id: FormControl<IDataTreeLeaf['id'] | NewDataTreeLeaf['id']>;
  stereoType: FormControl<IDataTreeLeaf['stereoType']>;
  name: FormControl<IDataTreeLeaf['name']>;
  caption: FormControl<IDataTreeLeaf['caption']>;
  documentation: FormControl<IDataTreeLeaf['documentation']>;
  leafToField: FormControl<IDataTreeLeaf['leafToField']>;
};

export type DataTreeLeafFormGroup = FormGroup<DataTreeLeafFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DataTreeLeafFormService {
  createDataTreeLeafFormGroup(dataTreeLeaf: DataTreeLeafFormGroupInput = { id: null }): DataTreeLeafFormGroup {
    const dataTreeLeafRawValue = {
      ...this.getFormDefaults(),
      ...dataTreeLeaf,
    };
    return new FormGroup<DataTreeLeafFormGroupContent>({
      id: new FormControl(
        { value: dataTreeLeafRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      stereoType: new FormControl(dataTreeLeafRawValue.stereoType, {
        validators: [Validators.required],
      }),
      name: new FormControl(dataTreeLeafRawValue.name, {
        validators: [Validators.required],
      }),
      caption: new FormControl(dataTreeLeafRawValue.caption, {
        validators: [Validators.required],
      }),
      documentation: new FormControl(dataTreeLeafRawValue.documentation),
      leafToField: new FormControl(dataTreeLeafRawValue.leafToField),
    });
  }

  getDataTreeLeaf(form: DataTreeLeafFormGroup): IDataTreeLeaf | NewDataTreeLeaf {
    return form.getRawValue() as IDataTreeLeaf | NewDataTreeLeaf;
  }

  resetForm(form: DataTreeLeafFormGroup, dataTreeLeaf: DataTreeLeafFormGroupInput): void {
    const dataTreeLeafRawValue = { ...this.getFormDefaults(), ...dataTreeLeaf };
    form.reset(
      {
        ...dataTreeLeafRawValue,
        id: { value: dataTreeLeafRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DataTreeLeafFormDefaults {
    return {
      id: null,
    };
  }
}
