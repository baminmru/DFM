import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRequestInfo } from 'app/entities/request-info/request-info.model';
import { RequestInfoService } from 'app/entities/request-info/service/request-info.service';
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

  requestInfosSharedCollection: IRequestInfo[] = [];

  protected requestContentService = inject(RequestContentService);
  protected requestContentFormService = inject(RequestContentFormService);
  protected requestInfoService = inject(RequestInfoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RequestContentFormGroup = this.requestContentFormService.createRequestContentFormGroup();

  compareRequestInfo = (o1: IRequestInfo | null, o2: IRequestInfo | null): boolean => this.requestInfoService.compareRequestInfo(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ requestContent }) => {
      this.requestContent = requestContent;
      if (requestContent) {
        this.updateForm(requestContent);
      }

      this.loadRelationshipsOptions();
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

    this.requestInfosSharedCollection = this.requestInfoService.addRequestInfoToCollectionIfMissing<IRequestInfo>(
      this.requestInfosSharedCollection,
      requestContent.requestInfoId,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.requestInfoService
      .query()
      .pipe(map((res: HttpResponse<IRequestInfo[]>) => res.body ?? []))
      .pipe(
        map((requestInfos: IRequestInfo[]) =>
          this.requestInfoService.addRequestInfoToCollectionIfMissing<IRequestInfo>(requestInfos, this.requestContent?.requestInfoId),
        ),
      )
      .subscribe((requestInfos: IRequestInfo[]) => (this.requestInfosSharedCollection = requestInfos));
  }
}
