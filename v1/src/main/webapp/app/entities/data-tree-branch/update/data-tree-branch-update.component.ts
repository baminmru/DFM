import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { DataTreeBranchFormService, DataTreeBranchFormGroup } from './data-tree-branch-form.service';
import { IDataTreeBranch } from '../data-tree-branch.model';
import { DataTreeBranchService } from '../service/data-tree-branch.service';
import { IDataTreeLeaf } from 'app/entities/data-tree-leaf/data-tree-leaf.model';
import { DataTreeLeafService } from 'app/entities/data-tree-leaf/service/data-tree-leaf.service';
import { IDataTreeBranchToField } from 'app/entities/data-tree-branch-to-field/data-tree-branch-to-field.model';
import { DataTreeBranchToFieldService } from 'app/entities/data-tree-branch-to-field/service/data-tree-branch-to-field.service';
import { IDataTreeBranchLink } from 'app/entities/data-tree-branch-link/data-tree-branch-link.model';
import { DataTreeBranchLinkService } from 'app/entities/data-tree-branch-link/service/data-tree-branch-link.service';
import { StereoTypeEnum } from 'app/entities/enumerations/stereo-type-enum.model';

@Component({
  standalone: true,
  selector: 'jhi-data-tree-branch-update',
  templateUrl: './data-tree-branch-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DataTreeBranchUpdateComponent implements OnInit {
  isSaving = false;
  dataTreeBranch: IDataTreeBranch | null = null;
  stereoTypeEnumValues = Object.keys(StereoTypeEnum);

  dataTreeLeavesSharedCollection: IDataTreeLeaf[] = [];
  dataTreeBranchToFieldsSharedCollection: IDataTreeBranchToField[] = [];
  dataTreeBranchLinksSharedCollection: IDataTreeBranchLink[] = [];

  editForm: DataTreeBranchFormGroup = this.dataTreeBranchFormService.createDataTreeBranchFormGroup();

  constructor(
    protected dataTreeBranchService: DataTreeBranchService,
    protected dataTreeBranchFormService: DataTreeBranchFormService,
    protected dataTreeLeafService: DataTreeLeafService,
    protected dataTreeBranchToFieldService: DataTreeBranchToFieldService,
    protected dataTreeBranchLinkService: DataTreeBranchLinkService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDataTreeLeaf = (o1: IDataTreeLeaf | null, o2: IDataTreeLeaf | null): boolean =>
    this.dataTreeLeafService.compareDataTreeLeaf(o1, o2);

  compareDataTreeBranchToField = (o1: IDataTreeBranchToField | null, o2: IDataTreeBranchToField | null): boolean =>
    this.dataTreeBranchToFieldService.compareDataTreeBranchToField(o1, o2);

  compareDataTreeBranchLink = (o1: IDataTreeBranchLink | null, o2: IDataTreeBranchLink | null): boolean =>
    this.dataTreeBranchLinkService.compareDataTreeBranchLink(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dataTreeBranch }) => {
      this.dataTreeBranch = dataTreeBranch;
      if (dataTreeBranch) {
        this.updateForm(dataTreeBranch);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dataTreeBranch = this.dataTreeBranchFormService.getDataTreeBranch(this.editForm);
    if (dataTreeBranch.id !== null) {
      this.subscribeToSaveResponse(this.dataTreeBranchService.update(dataTreeBranch));
    } else {
      this.subscribeToSaveResponse(this.dataTreeBranchService.create(dataTreeBranch));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataTreeBranch>>): void {
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

  protected updateForm(dataTreeBranch: IDataTreeBranch): void {
    this.dataTreeBranch = dataTreeBranch;
    this.dataTreeBranchFormService.resetForm(this.editForm, dataTreeBranch);

    this.dataTreeLeavesSharedCollection = this.dataTreeLeafService.addDataTreeLeafToCollectionIfMissing<IDataTreeLeaf>(
      this.dataTreeLeavesSharedCollection,
      dataTreeBranch.dataTreeLeaf
    );
    this.dataTreeBranchToFieldsSharedCollection =
      this.dataTreeBranchToFieldService.addDataTreeBranchToFieldToCollectionIfMissing<IDataTreeBranchToField>(
        this.dataTreeBranchToFieldsSharedCollection,
        dataTreeBranch.branchToField
      );
    this.dataTreeBranchLinksSharedCollection =
      this.dataTreeBranchLinkService.addDataTreeBranchLinkToCollectionIfMissing<IDataTreeBranchLink>(
        this.dataTreeBranchLinksSharedCollection,
        dataTreeBranch.branchParent
      );
  }

  protected loadRelationshipsOptions(): void {
    this.dataTreeLeafService
      .query()
      .pipe(map((res: HttpResponse<IDataTreeLeaf[]>) => res.body ?? []))
      .pipe(
        map((dataTreeLeaves: IDataTreeLeaf[]) =>
          this.dataTreeLeafService.addDataTreeLeafToCollectionIfMissing<IDataTreeLeaf>(dataTreeLeaves, this.dataTreeBranch?.dataTreeLeaf)
        )
      )
      .subscribe((dataTreeLeaves: IDataTreeLeaf[]) => (this.dataTreeLeavesSharedCollection = dataTreeLeaves));

    this.dataTreeBranchToFieldService
      .query()
      .pipe(map((res: HttpResponse<IDataTreeBranchToField[]>) => res.body ?? []))
      .pipe(
        map((dataTreeBranchToFields: IDataTreeBranchToField[]) =>
          this.dataTreeBranchToFieldService.addDataTreeBranchToFieldToCollectionIfMissing<IDataTreeBranchToField>(
            dataTreeBranchToFields,
            this.dataTreeBranch?.branchToField
          )
        )
      )
      .subscribe(
        (dataTreeBranchToFields: IDataTreeBranchToField[]) => (this.dataTreeBranchToFieldsSharedCollection = dataTreeBranchToFields)
      );

    this.dataTreeBranchLinkService
      .query()
      .pipe(map((res: HttpResponse<IDataTreeBranchLink[]>) => res.body ?? []))
      .pipe(
        map((dataTreeBranchLinks: IDataTreeBranchLink[]) =>
          this.dataTreeBranchLinkService.addDataTreeBranchLinkToCollectionIfMissing<IDataTreeBranchLink>(
            dataTreeBranchLinks,
            this.dataTreeBranch?.branchParent
          )
        )
      )
      .subscribe((dataTreeBranchLinks: IDataTreeBranchLink[]) => (this.dataTreeBranchLinksSharedCollection = dataTreeBranchLinks));
  }
}
