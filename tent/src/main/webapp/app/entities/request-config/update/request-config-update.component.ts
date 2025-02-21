import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRequestContentConfig } from 'app/entities/request-content-config/request-content-config.model';
import { RequestContentConfigService } from 'app/entities/request-content-config/service/request-content-config.service';
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

  requestContentConfigsSharedCollection: IRequestContentConfig[] = [];

  protected requestConfigService = inject(RequestConfigService);
  protected requestConfigFormService = inject(RequestConfigFormService);
  protected requestContentConfigService = inject(RequestContentConfigService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RequestConfigFormGroup = this.requestConfigFormService.createRequestConfigFormGroup();

  compareRequestContentConfig = (o1: IRequestContentConfig | null, o2: IRequestContentConfig | null): boolean =>
    this.requestContentConfigService.compareRequestContentConfig(o1, o2);

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

    this.requestContentConfigsSharedCollection =
      this.requestContentConfigService.addRequestContentConfigToCollectionIfMissing<IRequestContentConfig>(
        this.requestContentConfigsSharedCollection,
        requestConfig.requestContentConfig,
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
            this.requestConfig?.requestContentConfig,
          ),
        ),
      )
      .subscribe((requestContentConfigs: IRequestContentConfig[]) => (this.requestContentConfigsSharedCollection = requestContentConfigs));
  }
}
