import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRequestParamDict, NewRequestParamDict } from '../request-param-dict.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRequestParamDict for edit and NewRequestParamDictFormGroupInput for create.
 */
type RequestParamDictFormGroupInput = IRequestParamDict | PartialWithRequiredKeyOf<NewRequestParamDict>;

type RequestParamDictFormDefaults = Pick<NewRequestParamDict, 'id' | 'valueArray'>;

type RequestParamDictFormGroupContent = {
  id: FormControl<IRequestParamDict['id'] | NewRequestParamDict['id']>;
  code: FormControl<IRequestParamDict['code']>;
  name: FormControl<IRequestParamDict['name']>;
  paramtype: FormControl<IRequestParamDict['paramtype']>;
  valueArray: FormControl<IRequestParamDict['valueArray']>;
  referenceTo: FormControl<IRequestParamDict['referenceTo']>;
  requestContentConfig: FormControl<IRequestParamDict['requestContentConfig']>;
};

export type RequestParamDictFormGroup = FormGroup<RequestParamDictFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RequestParamDictFormService {
  createRequestParamDictFormGroup(requestParamDict: RequestParamDictFormGroupInput = { id: null }): RequestParamDictFormGroup {
    const requestParamDictRawValue = {
      ...this.getFormDefaults(),
      ...requestParamDict,
    };
    return new FormGroup<RequestParamDictFormGroupContent>({
      id: new FormControl(
        { value: requestParamDictRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      code: new FormControl(requestParamDictRawValue.code, {
        validators: [Validators.maxLength(40)],
      }),
      name: new FormControl(requestParamDictRawValue.name, {
        validators: [Validators.required, Validators.maxLength(255)],
      }),
      paramtype: new FormControl(requestParamDictRawValue.paramtype, {
        validators: [Validators.maxLength(64)],
      }),
      valueArray: new FormControl(requestParamDictRawValue.valueArray),
      referenceTo: new FormControl(requestParamDictRawValue.referenceTo, {
        validators: [Validators.maxLength(64)],
      }),
      requestContentConfig: new FormControl(requestParamDictRawValue.requestContentConfig),
    });
  }

  getRequestParamDict(form: RequestParamDictFormGroup): IRequestParamDict | NewRequestParamDict {
    return form.getRawValue() as IRequestParamDict | NewRequestParamDict;
  }

  resetForm(form: RequestParamDictFormGroup, requestParamDict: RequestParamDictFormGroupInput): void {
    const requestParamDictRawValue = { ...this.getFormDefaults(), ...requestParamDict };
    form.reset(
      {
        ...requestParamDictRawValue,
        id: { value: requestParamDictRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RequestParamDictFormDefaults {
    return {
      id: null,
      valueArray: false,
    };
  }
}
