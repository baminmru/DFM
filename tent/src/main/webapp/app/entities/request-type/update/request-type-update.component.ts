import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRequestInfo } from 'app/entities/request-info/request-info.model';
import { RequestInfoService } from 'app/entities/request-info/service/request-info.service';
import { IRequestConfig } from 'app/entities/request-config/request-config.model';
import { RequestConfigService } from 'app/entities/request-config/service/request-config.service';
import { RequestTypeService } from '../service/request-type.service';
import { IRequestType } from '../request-type.model';
import { RequestTypeFormService, RequestTypeFormGroup } from './request-type-form.service';

@Component({
  standalone: true,
  selector: 'jhi-request-type-update',
  templateUrl: './request-type-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RequestTypeUpdateComponent implements OnInit {
  isSaving = false;
  requestType: IRequestType | null = null;

  requestInfosSharedCollection: IRequestInfo[] = [];
  requestConfigsSharedCollection: IRequestConfig[] = [];

  protected requestTypeService = inject(RequestTypeService);
  protected requestTypeFormService = inject(RequestTypeFormService);
  protected requestInfoService = inject(RequestInfoService);
  protected requestConfigService = inject(RequestConfigService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RequestTypeFormGroup = this.requestTypeFormService.createRequestTypeFormGroup();

  compareRequestInfo = (o1: IRequestInfo | null, o2: IRequestInfo | null): boolean => this.requestInfoService.compareRequestInfo(o1, o2);

  compareRequestConfig = (o1: IRequestConfig | null, o2: IRequestConfig | null): boolean =>
    this.requestConfigService.compareRequestConfig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requestType }) => {
      this.requestType = requestType;
      if (requestType) {
        this.updateForm(requestType);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const requestType = this.requestTypeFormService.getRequestType(this.editForm);
    if (requestType.id !== null) {
      this.subscribeToSaveResponse(this.requestTypeService.update(requestType));
    } else {
      this.subscribeToSaveResponse(this.requestTypeService.create(requestType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequestType>>): void {
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

  protected updateForm(requestType: IRequestType): void {
    this.requestType = requestType;
    this.requestTypeFormService.resetForm(this.editForm, requestType);

    this.requestInfosSharedCollection = this.requestInfoService.addRequestInfoToCollectionIfMissing<IRequestInfo>(
      this.requestInfosSharedCollection,
      requestType.requestInfo,
    );
    this.requestConfigsSharedCollection = this.requestConfigService.addRequestConfigToCollectionIfMissing<IRequestConfig>(
      this.requestConfigsSharedCollection,
      requestType.requestConfig,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.requestInfoService
      .query()
      .pipe(map((res: HttpResponse<IRequestInfo[]>) => res.body ?? []))
      .pipe(
        map((requestInfos: IRequestInfo[]) =>
          this.requestInfoService.addRequestInfoToCollectionIfMissing<IRequestInfo>(requestInfos, this.requestType?.requestInfo),
        ),
      )
      .subscribe((requestInfos: IRequestInfo[]) => (this.requestInfosSharedCollection = requestInfos));

    this.requestConfigService
      .query()
      .pipe(map((res: HttpResponse<IRequestConfig[]>) => res.body ?? []))
      .pipe(
        map((requestConfigs: IRequestConfig[]) =>
          this.requestConfigService.addRequestConfigToCollectionIfMissing<IRequestConfig>(requestConfigs, this.requestType?.requestConfig),
        ),
      )
      .subscribe((requestConfigs: IRequestConfig[]) => (this.requestConfigsSharedCollection = requestConfigs));
  }
}
