import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRequestConfig, NewRequestConfig } from '../request-config.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRequestConfig for edit and NewRequestConfigFormGroupInput for create.
 */
type RequestConfigFormGroupInput = IRequestConfig | PartialWithRequiredKeyOf<NewRequestConfig>;

type RequestConfigFormDefaults = Pick<NewRequestConfig, 'id'>;

type RequestConfigFormGroupContent = {
  id: FormControl<IRequestConfig['id'] | NewRequestConfig['id']>;
  requestType: FormControl<IRequestConfig['requestType']>;
};

export type RequestConfigFormGroup = FormGroup<RequestConfigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RequestConfigFormService {
  createRequestConfigFormGroup(requestConfig: RequestConfigFormGroupInput = { id: null }): RequestConfigFormGroup {
    const requestConfigRawValue = {
      ...this.getFormDefaults(),
      ...requestConfig,
    };
    return new FormGroup<RequestConfigFormGroupContent>({
      id: new FormControl(
        { value: requestConfigRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      requestType: new FormControl(requestConfigRawValue.requestType),
    });
  }

  getRequestConfig(form: RequestConfigFormGroup): IRequestConfig | NewRequestConfig {
    return form.getRawValue() as IRequestConfig | NewRequestConfig;
  }

  resetForm(form: RequestConfigFormGroup, requestConfig: RequestConfigFormGroupInput): void {
    const requestConfigRawValue = { ...this.getFormDefaults(), ...requestConfig };
    form.reset(
      {
        ...requestConfigRawValue,
        id: { value: requestConfigRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RequestConfigFormDefaults {
    return {
      id: null,
    };
  }
}
