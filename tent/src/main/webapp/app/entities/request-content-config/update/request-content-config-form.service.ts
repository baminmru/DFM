import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRequestContentConfig, NewRequestContentConfig } from '../request-content-config.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRequestContentConfig for edit and NewRequestContentConfigFormGroupInput for create.
 */
type RequestContentConfigFormGroupInput = IRequestContentConfig | PartialWithRequiredKeyOf<NewRequestContentConfig>;

type RequestContentConfigFormDefaults = Pick<NewRequestContentConfig, 'id' | 'isMandatory'>;

type RequestContentConfigFormGroupContent = {
  id: FormControl<IRequestContentConfig['id'] | NewRequestContentConfig['id']>;
  isMandatory: FormControl<IRequestContentConfig['isMandatory']>;
  requestConfigId: FormControl<IRequestContentConfig['requestConfigId']>;
  parameter: FormControl<IRequestContentConfig['parameter']>;
};

export type RequestContentConfigFormGroup = FormGroup<RequestContentConfigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RequestContentConfigFormService {
  createRequestContentConfigFormGroup(
    requestContentConfig: RequestContentConfigFormGroupInput = { id: null },
  ): RequestContentConfigFormGroup {
    const requestContentConfigRawValue = {
      ...this.getFormDefaults(),
      ...requestContentConfig,
    };
    return new FormGroup<RequestContentConfigFormGroupContent>({
      id: new FormControl(
        { value: requestContentConfigRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      isMandatory: new FormControl(requestContentConfigRawValue.isMandatory),
      requestConfigId: new FormControl(requestContentConfigRawValue.requestConfigId),
      parameter: new FormControl(requestContentConfigRawValue.parameter),
    });
  }

  getRequestContentConfig(form: RequestContentConfigFormGroup): IRequestContentConfig | NewRequestContentConfig {
    return form.getRawValue() as IRequestContentConfig | NewRequestContentConfig;
  }

  resetForm(form: RequestContentConfigFormGroup, requestContentConfig: RequestContentConfigFormGroupInput): void {
    const requestContentConfigRawValue = { ...this.getFormDefaults(), ...requestContentConfig };
    form.reset(
      {
        ...requestContentConfigRawValue,
        id: { value: requestContentConfigRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RequestContentConfigFormDefaults {
    return {
      id: null,
      isMandatory: false,
    };
  }
}
