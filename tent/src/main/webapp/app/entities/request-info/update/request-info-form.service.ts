import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRequestInfo, NewRequestInfo } from '../request-info.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRequestInfo for edit and NewRequestInfoFormGroupInput for create.
 */
type RequestInfoFormGroupInput = IRequestInfo | PartialWithRequiredKeyOf<NewRequestInfo>;

type RequestInfoFormDefaults = Pick<NewRequestInfo, 'id'>;

type RequestInfoFormGroupContent = {
  id: FormControl<IRequestInfo['id'] | NewRequestInfo['id']>;
  requestType: FormControl<IRequestInfo['requestType']>;
  contract: FormControl<IRequestInfo['contract']>;
  requestDate: FormControl<IRequestInfo['requestDate']>;
  effectiveDateStart: FormControl<IRequestInfo['effectiveDateStart']>;
  effectiveDateEnd: FormControl<IRequestInfo['effectiveDateEnd']>;
  createdAt: FormControl<IRequestInfo['createdAt']>;
  createdBy: FormControl<IRequestInfo['createdBy']>;
  updatedAt: FormControl<IRequestInfo['updatedAt']>;
  updatedBy: FormControl<IRequestInfo['updatedBy']>;
  requestContent: FormControl<IRequestInfo['requestContent']>;
};

export type RequestInfoFormGroup = FormGroup<RequestInfoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RequestInfoFormService {
  createRequestInfoFormGroup(requestInfo: RequestInfoFormGroupInput = { id: null }): RequestInfoFormGroup {
    const requestInfoRawValue = {
      ...this.getFormDefaults(),
      ...requestInfo,
    };
    return new FormGroup<RequestInfoFormGroupContent>({
      id: new FormControl(
        { value: requestInfoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      requestType: new FormControl(requestInfoRawValue.requestType, {
        validators: [Validators.required],
      }),
      contract: new FormControl(requestInfoRawValue.contract),
      requestDate: new FormControl(requestInfoRawValue.requestDate, {
        validators: [Validators.required],
      }),
      effectiveDateStart: new FormControl(requestInfoRawValue.effectiveDateStart),
      effectiveDateEnd: new FormControl(requestInfoRawValue.effectiveDateEnd),
      createdAt: new FormControl(requestInfoRawValue.createdAt),
      createdBy: new FormControl(requestInfoRawValue.createdBy, {
        validators: [Validators.maxLength(64)],
      }),
      updatedAt: new FormControl(requestInfoRawValue.updatedAt),
      updatedBy: new FormControl(requestInfoRawValue.updatedBy, {
        validators: [Validators.maxLength(64)],
      }),
      requestContent: new FormControl(requestInfoRawValue.requestContent),
    });
  }

  getRequestInfo(form: RequestInfoFormGroup): IRequestInfo | NewRequestInfo {
    return form.getRawValue() as IRequestInfo | NewRequestInfo;
  }

  resetForm(form: RequestInfoFormGroup, requestInfo: RequestInfoFormGroupInput): void {
    const requestInfoRawValue = { ...this.getFormDefaults(), ...requestInfo };
    form.reset(
      {
        ...requestInfoRawValue,
        id: { value: requestInfoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RequestInfoFormDefaults {
    return {
      id: null,
    };
  }
}
