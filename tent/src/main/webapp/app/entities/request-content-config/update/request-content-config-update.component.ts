import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRequestConfig } from 'app/entities/request-config/request-config.model';
import { RequestConfigService } from 'app/entities/request-config/service/request-config.service';
import { IRequestParamDict } from 'app/entities/request-param-dict/request-param-dict.model';
import { RequestParamDictService } from 'app/entities/request-param-dict/service/request-param-dict.service';
import { RequestContentConfigService } from '../service/request-content-config.service';
import { IRequestContentConfig } from '../request-content-config.model';
import { RequestContentConfigFormService, RequestContentConfigFormGroup } from './request-content-config-form.service';

@Component({
  standalone: true,
  selector: 'jhi-request-content-config-update',
  templateUrl: './request-content-config-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RequestContentConfigUpdateComponent implements OnInit {
  isSaving = false;
  requestContentConfig: IRequestContentConfig | null = null;

  requestConfigsSharedCollection: IRequestConfig[] = [];
  requestParamDictsSharedCollection: IRequestParamDict[] = [];

  protected requestContentConfigService = inject(RequestContentConfigService);
  protected requestContentConfigFormService = inject(RequestContentConfigFormService);
  protected requestConfigService = inject(RequestConfigService);
  protected requestParamDictService = inject(RequestParamDictService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RequestContentConfigFormGroup = this.requestContentConfigFormService.createRequestContentConfigFormGroup();

  compareRequestConfig = (o1: IRequestConfig | null, o2: IRequestConfig | null): boolean =>
    this.requestConfigService.compareRequestConfig(o1, o2);

  compareRequestParamDict = (o1: IRequestParamDict | null, o2: IRequestParamDict | null): boolean =>
    this.requestParamDictService.compareRequestParamDict(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requestContentConfig }) => {
      this.requestContentConfig = requestContentConfig;
      if (requestContentConfig) {
        this.updateForm(requestContentConfig);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const requestContentConfig = this.requestContentConfigFormService.getRequestContentConfig(this.editForm);
    if (requestContentConfig.id !== null) {
      this.subscribeToSaveResponse(this.requestContentConfigService.update(requestContentConfig));
    } else {
      this.subscribeToSaveResponse(this.requestContentConfigService.create(requestContentConfig));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequestContentConfig>>): void {
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

  protected updateForm(requestContentConfig: IRequestContentConfig): void {
    this.requestContentConfig = requestContentConfig;
    this.requestContentConfigFormService.resetForm(this.editForm, requestContentConfig);

    this.requestConfigsSharedCollection = this.requestConfigService.addRequestConfigToCollectionIfMissing<IRequestConfig>(
      this.requestConfigsSharedCollection,
      requestContentConfig.requestConfigId,
    );
    this.requestParamDictsSharedCollection = this.requestParamDictService.addRequestParamDictToCollectionIfMissing<IRequestParamDict>(
      this.requestParamDictsSharedCollection,
      requestContentConfig.parameter,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.requestConfigService
      .query()
      .pipe(map((res: HttpResponse<IRequestConfig[]>) => res.body ?? []))
      .pipe(
        map((requestConfigs: IRequestConfig[]) =>
          this.requestConfigService.addRequestConfigToCollectionIfMissing<IRequestConfig>(
            requestConfigs,
            this.requestContentConfig?.requestConfigId,
          ),
        ),
      )
      .subscribe((requestConfigs: IRequestConfig[]) => (this.requestConfigsSharedCollection = requestConfigs));

    this.requestParamDictService
      .query()
      .pipe(map((res: HttpResponse<IRequestParamDict[]>) => res.body ?? []))
      .pipe(
        map((requestParamDicts: IRequestParamDict[]) =>
          this.requestParamDictService.addRequestParamDictToCollectionIfMissing<IRequestParamDict>(
            requestParamDicts,
            this.requestContentConfig?.parameter,
          ),
        ),
      )
      .subscribe((requestParamDicts: IRequestParamDict[]) => (this.requestParamDictsSharedCollection = requestParamDicts));
  }
}
