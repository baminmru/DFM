import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRequestContent } from 'app/entities/request-content/request-content.model';
import { RequestContentService } from 'app/entities/request-content/service/request-content.service';
import { IRequestInfo } from '../request-info.model';
import { RequestInfoService } from '../service/request-info.service';
import { RequestInfoFormService, RequestInfoFormGroup } from './request-info-form.service';

@Component({
  standalone: true,
  selector: 'jhi-request-info-update',
  templateUrl: './request-info-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class RequestInfoUpdateComponent implements OnInit {
  isSaving = false;
  requestInfo: IRequestInfo | null = null;

  requestContentsSharedCollection: IRequestContent[] = [];

  protected requestInfoService = inject(RequestInfoService);
  protected requestInfoFormService = inject(RequestInfoFormService);
  protected requestContentService = inject(RequestContentService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RequestInfoFormGroup = this.requestInfoFormService.createRequestInfoFormGroup();

  compareRequestContent = (o1: IRequestContent | null, o2: IRequestContent | null): boolean =>
    this.requestContentService.compareRequestContent(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requestInfo }) => {
      this.requestInfo = requestInfo;
      if (requestInfo) {
        this.updateForm(requestInfo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const requestInfo = this.requestInfoFormService.getRequestInfo(this.editForm);
    if (requestInfo.id !== null) {
      this.subscribeToSaveResponse(this.requestInfoService.update(requestInfo));
    } else {
      this.subscribeToSaveResponse(this.requestInfoService.create(requestInfo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequestInfo>>): void {
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

  protected updateForm(requestInfo: IRequestInfo): void {
    this.requestInfo = requestInfo;
    this.requestInfoFormService.resetForm(this.editForm, requestInfo);

    this.requestContentsSharedCollection = this.requestContentService.addRequestContentToCollectionIfMissing<IRequestContent>(
      this.requestContentsSharedCollection,
      requestInfo.requestContent,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.requestContentService
      .query()
      .pipe(map((res: HttpResponse<IRequestContent[]>) => res.body ?? []))
      .pipe(
        map((requestContents: IRequestContent[]) =>
          this.requestContentService.addRequestContentToCollectionIfMissing<IRequestContent>(
            requestContents,
            this.requestInfo?.requestContent,
          ),
        ),
      )
      .subscribe((requestContents: IRequestContent[]) => (this.requestContentsSharedCollection = requestContents));
  }
}
