import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRequestType } from 'app/entities/request-type/request-type.model';
import { RequestTypeService } from 'app/entities/request-type/service/request-type.service';
import { IRequestConfig } from '../request-config.model';
import { RequestConfigService } from '../service/request-config.service';
import { RequestConfigFormService, RequestConfigFormGroup } from './request-config-form.service';

@Component({
  standalone: true,
  selector: 'jhi-request-config-update',
  templateUrl: './request-config-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RequestConfigUpdateComponent implements OnInit {
  isSaving = false;
  requestConfig: IRequestConfig | null = null;

  requestTypesSharedCollection: IRequestType[] = [];

  protected requestConfigService = inject(RequestConfigService);
  protected requestConfigFormService = inject(RequestConfigFormService);
  protected requestTypeService = inject(RequestTypeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RequestConfigFormGroup = this.requestConfigFormService.createRequestConfigFormGroup();

  compareRequestType = (o1: IRequestType | null, o2: IRequestType | null): boolean => this.requestTypeService.compareRequestType(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requestConfig }) => {
      this.requestConfig = requestConfig;
      if (requestConfig) {
        this.updateForm(requestConfig);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const requestConfig = this.requestConfigFormService.getRequestConfig(this.editForm);
    if (requestConfig.id !== null) {
      this.subscribeToSaveResponse(this.requestConfigService.update(requestConfig));
    } else {
      this.subscribeToSaveResponse(this.requestConfigService.create(requestConfig));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequestConfig>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(requestConfig: IRequestConfig): void {
    this.requestConfig = requestConfig;
    this.requestConfigFormService.resetForm(this.editForm, requestConfig);

    this.requestTypesSharedCollection = this.requestTypeService.addRequestTypeToCollectionIfMissing<IRequestType>(
      this.requestTypesSharedCollection,
      requestConfig.requestType,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.requestTypeService
      .query()
      .pipe(map((res: HttpResponse<IRequestType[]>) => res.body ?? []))
      .pipe(
        map((requestTypes: IRequestType[]) =>
          this.requestTypeService.addRequestTypeToCollectionIfMissing<IRequestType>(requestTypes, this.requestConfig?.requestType),
        ),
      )
      .subscribe((requestTypes: IRequestType[]) => (this.requestTypesSharedCollection = requestTypes));
  }
}
