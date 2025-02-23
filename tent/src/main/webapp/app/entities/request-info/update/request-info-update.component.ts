import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IRequestType } from 'app/entities/request-type/request-type.model';
import { RequestTypeService } from 'app/entities/request-type/service/request-type.service';
import { ISourceSystem } from 'app/entities/source-system/source-system.model';
import { SourceSystemService } from 'app/entities/source-system/service/source-system.service';
import { RequestInfoService } from '../service/request-info.service';
import { IRequestInfo } from '../request-info.model';
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

  requestTypesSharedCollection: IRequestType[] = [];
  sourceSystemsSharedCollection: ISourceSystem[] = [];

  protected requestInfoService = inject(RequestInfoService);
  protected requestInfoFormService = inject(RequestInfoFormService);
  protected requestTypeService = inject(RequestTypeService);
  protected sourceSystemService = inject(SourceSystemService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: RequestInfoFormGroup = this.requestInfoFormService.createRequestInfoFormGroup();

  compareRequestType = (o1: IRequestType | null, o2: IRequestType | null): boolean => this.requestTypeService.compareRequestType(o1, o2);

  compareSourceSystem = (o1: ISourceSystem | null, o2: ISourceSystem | null): boolean =>
    this.sourceSystemService.compareSourceSystem(o1, o2);

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

    this.requestTypesSharedCollection = this.requestTypeService.addRequestTypeToCollectionIfMissing<IRequestType>(
      this.requestTypesSharedCollection,
      requestInfo.requestType,
    );
    this.sourceSystemsSharedCollection = this.sourceSystemService.addSourceSystemToCollectionIfMissing<ISourceSystem>(
      this.sourceSystemsSharedCollection,
      requestInfo.requestSource,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.requestTypeService
      .query()
      .pipe(map((res: HttpResponse<IRequestType[]>) => res.body ?? []))
      .pipe(
        map((requestTypes: IRequestType[]) =>
          this.requestTypeService.addRequestTypeToCollectionIfMissing<IRequestType>(requestTypes, this.requestInfo?.requestType),
        ),
      )
      .subscribe((requestTypes: IRequestType[]) => (this.requestTypesSharedCollection = requestTypes));

    this.sourceSystemService
      .query()
      .pipe(map((res: HttpResponse<ISourceSystem[]>) => res.body ?? []))
      .pipe(
        map((sourceSystems: ISourceSystem[]) =>
          this.sourceSystemService.addSourceSystemToCollectionIfMissing<ISourceSystem>(sourceSystems, this.requestInfo?.requestSource),
        ),
      )
      .subscribe((sourceSystems: ISourceSystem[]) => (this.sourceSystemsSharedCollection = sourceSystems));
  }
}
