import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRequestContentConfig } from 'app/entities/request-content-config/request-content-config.model';
import { RequestContentConfigService } from 'app/entities/request-content-config/service/request-content-config.service';
import { IRequestParamDict } from '../request-param-dict.model';
import { RequestParamDictService } from '../service/request-param-dict.service';
import { RequestParamDictFormService, RequestParamDictFormGroup } from './request-param-dict-form.service';

@Component({
  standalone: true,
  selector: 'jhi-request-param-dict-update',
  templateUrl: './request-param-dict-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RequestParamDictUpdateComponent implements OnInit {
  isSaving = false;
  requestParamDict: IRequestParamDict | null = null;

  requestContentConfigsSharedCollection: IRequestContentConfig[] = [];

  protected requestParamDictService = inject(RequestParamDictService);
  protected requestParamDictFormService = inject(RequestParamDictFormService);
  protected requestContentConfigService = inject(RequestContentConfigService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RequestParamDictFormGroup = this.requestParamDictFormService.createRequestParamDictFormGroup();

  compareRequestContentConfig = (o1: IRequestContentConfig | null, o2: IRequestContentConfig | null): boolean =>
    this.requestContentConfigService.compareRequestContentConfig(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requestParamDict }) => {
      this.requestParamDict = requestParamDict;
      if (requestParamDict) {
        this.updateForm(requestParamDict);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const requestParamDict = this.requestParamDictFormService.getRequestParamDict(this.editForm);
    if (requestParamDict.id !== null) {
      this.subscribeToSaveResponse(this.requestParamDictService.update(requestParamDict));
    } else {
      this.subscribeToSaveResponse(this.requestParamDictService.create(requestParamDict));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequestParamDict>>): void {
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

  protected updateForm(requestParamDict: IRequestParamDict): void {
    this.requestParamDict = requestParamDict;
    this.requestParamDictFormService.resetForm(this.editForm, requestParamDict);

    this.requestContentConfigsSharedCollection =
      this.requestContentConfigService.addRequestContentConfigToCollectionIfMissing<IRequestContentConfig>(
        this.requestContentConfigsSharedCollection,
        requestParamDict.requestContentConfig,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.requestContentConfigService
      .query()
      .pipe(map((res: HttpResponse<IRequestContentConfig[]>) => res.body ?? []))
      .pipe(
        map((requestContentConfigs: IRequestContentConfig[]) =>
          this.requestContentConfigService.addRequestContentConfigToCollectionIfMissing<IRequestContentConfig>(
            requestContentConfigs,
            this.requestParamDict?.requestContentConfig,
          ),
        ),
      )
      .subscribe((requestContentConfigs: IRequestContentConfig[]) => (this.requestContentConfigsSharedCollection = requestContentConfigs));
  }
}
