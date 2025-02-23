import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISourceSystem, NewSourceSystem } from '../source-system.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISourceSystem for edit and NewSourceSystemFormGroupInput for create.
 */
type SourceSystemFormGroupInput = ISourceSystem | PartialWithRequiredKeyOf<NewSourceSystem>;

type SourceSystemFormDefaults = Pick<NewSourceSystem, 'id'>;

type SourceSystemFormGroupContent = {
  id: FormControl<ISourceSystem['id'] | NewSourceSystem['id']>;
  code: FormControl<ISourceSystem['code']>;
  name: FormControl<ISourceSystem['name']>;
  createdAt: FormControl<ISourceSystem['createdAt']>;
  createdBy: FormControl<ISourceSystem['createdBy']>;
  updatedAt: FormControl<ISourceSystem['updatedAt']>;
  updatedBy: FormControl<ISourceSystem['updatedBy']>;
};

export type SourceSystemFormGroup = FormGroup<SourceSystemFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SourceSystemFormService {
  createSourceSystemFormGroup(sourceSystem: SourceSystemFormGroupInput = { id: null }): SourceSystemFormGroup {
    const sourceSystemRawValue = {
      ...this.getFormDefaults(),
      ...sourceSystem,
    };
    return new FormGroup<SourceSystemFormGroupContent>({
      id: new FormControl(
        { value: sourceSystemRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(sourceSystemRawValue.code, {
        validators: [Validators.required, Validators.maxLength(40)],
      }),
      name: new FormControl(sourceSystemRawValue.name, {
        validators: [Validators.maxLength(255)],
      }),
      createdAt: new FormControl(sourceSystemRawValue.createdAt),
      createdBy: new FormControl(sourceSystemRawValue.createdBy, {
        validators: [Validators.maxLength(64)],
      }),
      updatedAt: new FormControl(sourceSystemRawValue.updatedAt),
      updatedBy: new FormControl(sourceSystemRawValue.updatedBy, {
        validators: [Validators.maxLength(64)],
      }),
    });
  }

  getSourceSystem(form: SourceSystemFormGroup): ISourceSystem | NewSourceSystem {
    return form.getRawValue() as ISourceSystem | NewSourceSystem;
  }

  resetForm(form: SourceSystemFormGroup, sourceSystem: SourceSystemFormGroupInput): void {
    const sourceSystemRawValue = { ...this.getFormDefaults(), ...sourceSystem };
    form.reset(
      {
        ...sourceSystemRawValue,
        id: { value: sourceSystemRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SourceSystemFormDefaults {
    return {
      id: null,
    };
  }
}
