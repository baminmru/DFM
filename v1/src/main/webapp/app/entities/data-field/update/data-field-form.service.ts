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

type DataFieldFormDefaults = Pick<NewDataField, 'id' | 'isBrief' | 'allowNull'>;

type DataFieldFormGroupContent = {
  id: FormControl<IDataField['id'] | NewDataField['id']>;
  inputType: FormControl<IDataField['inputType']>;
  fieldType: FormControl<IDataField['fieldType']>;
  sequence: FormControl<IDataField['sequence']>;
  isBrief: FormControl<IDataField['isBrief']>;
  briefSequence: FormControl<IDataField['briefSequence']>;
  allowNull: FormControl<IDataField['allowNull']>;
  name: FormControl<IDataField['name']>;
  caption: FormControl<IDataField['caption']>;
  documentation: FormControl<IDataField['documentation']>;
  tabName: FormControl<IDataField['tabName']>;
  groupName: FormControl<IDataField['groupName']>;
  generationStyle: FormControl<IDataField['generationStyle']>;
  refToRoot: FormControl<IDataField['refToRoot']>;
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
      inputType: new FormControl(dataFieldRawValue.inputType, {
        validators: [Validators.required],
      }),
      fieldType: new FormControl(dataFieldRawValue.fieldType, {
        validators: [Validators.required],
      }),
      sequence: new FormControl(dataFieldRawValue.sequence, {
        validators: [Validators.required],
      }),
      isBrief: new FormControl(dataFieldRawValue.isBrief, {
        validators: [Validators.required],
      }),
      briefSequence: new FormControl(dataFieldRawValue.briefSequence),
      allowNull: new FormControl(dataFieldRawValue.allowNull, {
        validators: [Validators.required],
      }),
      name: new FormControl(dataFieldRawValue.name, {
        validators: [Validators.required],
      }),
      caption: new FormControl(dataFieldRawValue.caption, {
        validators: [Validators.required],
      }),
      documentation: new FormControl(dataFieldRawValue.documentation),
      tabName: new FormControl(dataFieldRawValue.tabName),
      groupName: new FormControl(dataFieldRawValue.groupName),
      generationStyle: new FormControl(dataFieldRawValue.generationStyle),
      refToRoot: new FormControl(dataFieldRawValue.refToRoot),
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
      isBrief: false,
      allowNull: false,
    };
  }
}
