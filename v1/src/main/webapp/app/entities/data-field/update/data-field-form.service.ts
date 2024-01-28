import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDataField, NewDataField } from '../data-field.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDataField for edit and NewDataFieldFormGroupInput for create.
 */
type DataFieldFormGroupInput = IDataField | PartialWithRequiredKeyOf<NewDataField>;

type DataFieldFormDefaults = Pick<NewDataField, 'id' | 'allowNull' | 'dataTreeRoots' | 'dataTreeBranches' | 'dataTreeLeaves'>;

type DataFieldFormGroupContent = {
  id: FormControl<IDataField['id'] | NewDataField['id']>;
  inputType: FormControl<IDataField['inputType']>;
  fieldType: FormControl<IDataField['fieldType']>;
  referenceRoot: FormControl<IDataField['referenceRoot']>;
  allowNull: FormControl<IDataField['allowNull']>;
  name: FormControl<IDataField['name']>;
  caption: FormControl<IDataField['caption']>;
  documentation: FormControl<IDataField['documentation']>;
  dataTreeRoots: FormControl<IDataField['dataTreeRoots']>;
  dataTreeBranches: FormControl<IDataField['dataTreeBranches']>;
  dataTreeLeaves: FormControl<IDataField['dataTreeLeaves']>;
};

export type DataFieldFormGroup = FormGroup<DataFieldFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DataFieldFormService {
  createDataFieldFormGroup(dataField: DataFieldFormGroupInput = { id: null }): DataFieldFormGroup {
    const dataFieldRawValue = {
      ...this.getFormDefaults(),
      ...dataField,
    };
    return new FormGroup<DataFieldFormGroupContent>({
      id: new FormControl(
        { value: dataFieldRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      inputType: new FormControl(dataFieldRawValue.inputType),
      fieldType: new FormControl(dataFieldRawValue.fieldType),
      referenceRoot: new FormControl(dataFieldRawValue.referenceRoot),
      allowNull: new FormControl(dataFieldRawValue.allowNull),
      name: new FormControl(dataFieldRawValue.name),
      caption: new FormControl(dataFieldRawValue.caption),
      documentation: new FormControl(dataFieldRawValue.documentation),
      dataTreeRoots: new FormControl(dataFieldRawValue.dataTreeRoots ?? []),
      dataTreeBranches: new FormControl(dataFieldRawValue.dataTreeBranches ?? []),
      dataTreeLeaves: new FormControl(dataFieldRawValue.dataTreeLeaves ?? []),
    });
  }

  getDataField(form: DataFieldFormGroup): IDataField | NewDataField {
    return form.getRawValue() as IDataField | NewDataField;
  }

  resetForm(form: DataFieldFormGroup, dataField: DataFieldFormGroupInput): void {
    const dataFieldRawValue = { ...this.getFormDefaults(), ...dataField };
    form.reset(
      {
        ...dataFieldRawValue,
        id: { value: dataFieldRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DataFieldFormDefaults {
    return {
      id: null,
      allowNull: false,
      dataTreeRoots: [],
      dataTreeBranches: [],
      dataTreeLeaves: [],
    };
  }
}
