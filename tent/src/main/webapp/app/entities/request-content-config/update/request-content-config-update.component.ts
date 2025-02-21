import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRequestContentConfig } from '../request-content-config.model';
import { RequestContentConfigService } from '../service/request-content-config.service';
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

  protected requestContentConfigService = inject(RequestContentConfigService);
  protected requestContentConfigFormService = inject(RequestContentConfigFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RequestContentConfigFormGroup = this.requestContentConfigFormService.createRequestContentConfigFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requestContentConfig }) => {
      this.requestContentConfig = requestContentConfig;
      if (requestContentConfig) {
        this.updateForm(requestContentConfig);
      }
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
  }
}
