import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRequestContent, NewRequestContent } from '../request-content.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRequestContent for edit and NewRequestContentFormGroupInput for create.
 */
type RequestContentFormGroupInput = IRequestContent | PartialWithRequiredKeyOf<NewRequestContent>;

type RequestContentFormDefaults = Pick<NewRequestContent, 'id'>;

type RequestContentFormGroupContent = {
  id: FormControl<IRequestContent['id'] | NewRequestContent['id']>;
  paramCode: FormControl<IRequestContent['paramCode']>;
  paramValue: FormControl<IRequestContent['paramValue']>;
  createdAt: FormControl<IRequestContent['createdAt']>;
  createdBy: FormControl<IRequestContent['createdBy']>;
  updatedAt: FormControl<IRequestContent['updatedAt']>;
  updatedBy: FormControl<IRequestContent['updatedBy']>;
  requestInfoId: FormControl<IRequestContent['requestInfoId']>;
};

export type RequestContentFormGroup = FormGroup<RequestContentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RequestContentFormService {
  createRequestContentFormGroup(requestContent: RequestContentFormGroupInput = { id: null }): RequestContentFormGroup {
    const requestContentRawValue = {
      ...this.getFormDefaults(),
      ...requestContent,
    };
    return new FormGroup<RequestContentFormGroupContent>({
      id: new FormControl(
        { value: requestContentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      paramCode: new FormControl(requestContentRawValue.paramCode, {
        validators: [Validators.required, Validators.maxLength(64)],
      }),
      paramValue: new FormControl(requestContentRawValue.paramValue, {
        validators: [Validators.maxLength(255)],
      }),
      createdAt: new FormControl(requestContentRawValue.createdAt),
      createdBy: new FormControl(requestContentRawValue.createdBy, {
        validators: [Validators.maxLength(64)],
      }),
      updatedAt: new FormControl(requestContentRawValue.updatedAt),
      updatedBy: new FormControl(requestContentRawValue.updatedBy, {
        validators: [Validators.maxLength(64)],
      }),
      requestInfoId: new FormControl(requestContentRawValue.requestInfoId),
    });
  }

  getRequestContent(form: RequestContentFormGroup): IRequestContent | NewRequestContent {
    return form.getRawValue() as IRequestContent | NewRequestContent;
  }

  resetForm(form: RequestContentFormGroup, requestContent: RequestContentFormGroupInput): void {
    const requestContentRawValue = { ...this.getFormDefaults(), ...requestContent };
    form.reset(
      {
        ...requestContentRawValue,
        id: { value: requestContentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RequestContentFormDefaults {
    return {
      id: null,
    };
  }
}
