import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRequestType, NewRequestType } from '../request-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRequestType for edit and NewRequestTypeFormGroupInput for create.
 */
type RequestTypeFormGroupInput = IRequestType | PartialWithRequiredKeyOf<NewRequestType>;

type RequestTypeFormDefaults = Pick<NewRequestType, 'id'>;

type RequestTypeFormGroupContent = {
  id: FormControl<IRequestType['id'] | NewRequestType['id']>;
  code: FormControl<IRequestType['code']>;
  name: FormControl<IRequestType['name']>;
};

export type RequestTypeFormGroup = FormGroup<RequestTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RequestTypeFormService {
  createRequestTypeFormGroup(requestType: RequestTypeFormGroupInput = { id: null }): RequestTypeFormGroup {
    const requestTypeRawValue = {
      ...this.getFormDefaults(),
      ...requestType,
    };
    return new FormGroup<RequestTypeFormGroupContent>({
      id: new FormControl(
        { value: requestTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(requestTypeRawValue.code, {
        validators: [Validators.required, Validators.maxLength(64)],
      }),
      name: new FormControl(requestTypeRawValue.name, {
        validators: [Validators.required, Validators.maxLength(255)],
      }),
    });
  }

  getRequestType(form: RequestTypeFormGroup): IRequestType | NewRequestType {
    return form.getRawValue() as IRequestType | NewRequestType;
  }

  resetForm(form: RequestTypeFormGroup, requestType: RequestTypeFormGroupInput): void {
    const requestTypeRawValue = { ...this.getFormDefaults(), ...requestType };
    form.reset(
      {
        ...requestTypeRawValue,
        id: { value: requestTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RequestTypeFormDefaults {
    return {
      id: null,
    };
  }
}
