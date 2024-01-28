import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDataForest, NewDataForest } from '../data-forest.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDataForest for edit and NewDataForestFormGroupInput for create.
 */
type DataForestFormGroupInput = IDataForest | PartialWithRequiredKeyOf<NewDataForest>;

type DataForestFormDefaults = Pick<NewDataForest, 'id'>;

type DataForestFormGroupContent = {
  id: FormControl<IDataForest['id'] | NewDataForest['id']>;
  name: FormControl<IDataForest['name']>;
  caption: FormControl<IDataForest['caption']>;
  documentation: FormControl<IDataForest['documentation']>;
  forestTrees: FormControl<IDataForest['forestTrees']>;
};

export type DataForestFormGroup = FormGroup<DataForestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DataForestFormService {
  createDataForestFormGroup(dataForest: DataForestFormGroupInput = { id: null }): DataForestFormGroup {
    const dataForestRawValue = {
      ...this.getFormDefaults(),
      ...dataForest,
    };
    return new FormGroup<DataForestFormGroupContent>({
      id: new FormControl(
        { value: dataForestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(dataForestRawValue.name),
      caption: new FormControl(dataForestRawValue.caption),
      documentation: new FormControl(dataForestRawValue.documentation),
      forestTrees: new FormControl(dataForestRawValue.forestTrees),
    });
  }

  getDataForest(form: DataForestFormGroup): IDataForest | NewDataForest {
    return form.getRawValue() as IDataForest | NewDataForest;
  }

  resetForm(form: DataForestFormGroup, dataForest: DataForestFormGroupInput): void {
    const dataForestRawValue = { ...this.getFormDefaults(), ...dataForest };
    form.reset(
      {
        ...dataForestRawValue,
        id: { value: dataForestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DataForestFormDefaults {
    return {
      id: null,
    };
  }
}
