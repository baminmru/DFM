import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRequestContent } from '../request-content.model';
import { RequestContentService } from '../service/request-content.service';
import { RequestContentFormService, RequestContentFormGroup } from './request-content-form.service';

@Component({
  standalone: true,
  selector: 'jhi-request-content-update',
  templateUrl: './request-content-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RequestContentUpdateComponent implements OnInit {
  isSaving = false;
  requestContent: IRequestContent | null = null;

  protected requestContentService = inject(RequestContentService);
  protected requestContentFormService = inject(RequestContentFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RequestContentFormGroup = this.requestContentFormService.createRequestContentFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requestContent }) => {
      this.requestContent = requestContent;
      if (requestContent) {
        this.updateForm(requestContent);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const requestContent = this.requestContentFormService.getRequestContent(this.editForm);
    if (requestContent.id !== null) {
      this.subscribeToSaveResponse(this.requestContentService.update(requestContent));
    } else {
      this.subscribeToSaveResponse(this.requestContentService.create(requestContent));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequestContent>>): void {
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

  protected updateForm(requestContent: IRequestContent): void {
    this.requestContent = requestContent;
    this.requestContentFormService.resetForm(this.editForm, requestContent);
  }
}
