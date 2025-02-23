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
  version: FormControl<IRequestConfig['version']>;
  effectiveDateStart: FormControl<IRequestConfig['effectiveDateStart']>;
  effectiveDateEnd: FormControl<IRequestConfig['effectiveDateEnd']>;
  createdAt: FormControl<IRequestConfig['createdAt']>;
  createdBy: FormControl<IRequestConfig['createdBy']>;
  updatedAt: FormControl<IRequestConfig['updatedAt']>;
  updatedBy: FormControl<IRequestConfig['updatedBy']>;
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
      version: new FormControl(requestConfigRawValue.version, {
        validators: [Validators.maxLength(40)],
      }),
      effectiveDateStart: new FormControl(requestConfigRawValue.effectiveDateStart),
      effectiveDateEnd: new FormControl(requestConfigRawValue.effectiveDateEnd),
      createdAt: new FormControl(requestConfigRawValue.createdAt),
      createdBy: new FormControl(requestConfigRawValue.createdBy, {
        validators: [Validators.maxLength(64)],
      }),
      updatedAt: new FormControl(requestConfigRawValue.updatedAt),
      updatedBy: new FormControl(requestConfigRawValue.updatedBy, {
        validators: [Validators.maxLength(64)],
      }),
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
