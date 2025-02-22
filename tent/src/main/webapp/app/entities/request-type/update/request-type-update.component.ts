import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRequestType } from '../request-type.model';
import { RequestTypeService } from '../service/request-type.service';
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

  protected requestTypeService = inject(RequestTypeService);
  protected requestTypeFormService = inject(RequestTypeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RequestTypeFormGroup = this.requestTypeFormService.createRequestTypeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requestType }) => {
      this.requestType = requestType;
      if (requestType) {
        this.updateForm(requestType);
      }
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
  }
}
