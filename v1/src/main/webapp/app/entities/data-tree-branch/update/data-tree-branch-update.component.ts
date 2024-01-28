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
import { IDataField } from 'app/entities/data-field/data-field.model';
import { DataFieldService } from 'app/entities/data-field/service/data-field.service';
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
  dataFieldsSharedCollection: IDataField[] = [];
  dataTreeBranchesSharedCollection: IDataTreeBranch[] = [];

  editForm: DataTreeBranchFormGroup = this.dataTreeBranchFormService.createDataTreeBranchFormGroup();

  constructor(
    protected dataTreeBranchService: DataTreeBranchService,
    protected dataTreeBranchFormService: DataTreeBranchFormService,
    protected dataTreeLeafService: DataTreeLeafService,
    protected dataFieldService: DataFieldService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDataTreeLeaf = (o1: IDataTreeLeaf | null, o2: IDataTreeLeaf | null): boolean =>
    this.dataTreeLeafService.compareDataTreeLeaf(o1, o2);

  compareDataField = (o1: IDataField | null, o2: IDataField | null): boolean => this.dataFieldService.compareDataField(o1, o2);

  compareDataTreeBranch = (o1: IDataTreeBranch | null, o2: IDataTreeBranch | null): boolean =>
    this.dataTreeBranchService.compareDataTreeBranch(o1, o2);

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
    this.dataFieldsSharedCollection = this.dataFieldService.addDataFieldToCollectionIfMissing<IDataField>(
      this.dataFieldsSharedCollection,
      ...(dataTreeBranch.branchToFields ?? [])
    );
    this.dataTreeBranchesSharedCollection = this.dataTreeBranchService.addDataTreeBranchToCollectionIfMissing<IDataTreeBranch>(
      this.dataTreeBranchesSharedCollection,
      ...(dataTreeBranch.branchParents ?? [])
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

    this.dataFieldService
      .query()
      .pipe(map((res: HttpResponse<IDataField[]>) => res.body ?? []))
      .pipe(
        map((dataFields: IDataField[]) =>
          this.dataFieldService.addDataFieldToCollectionIfMissing<IDataField>(dataFields, ...(this.dataTreeBranch?.branchToFields ?? []))
        )
      )
      .subscribe((dataFields: IDataField[]) => (this.dataFieldsSharedCollection = dataFields));

    this.dataTreeBranchService
      .query()
      .pipe(map((res: HttpResponse<IDataTreeBranch[]>) => res.body ?? []))
      .pipe(
        map((dataTreeBranches: IDataTreeBranch[]) =>
          this.dataTreeBranchService.addDataTreeBranchToCollectionIfMissing<IDataTreeBranch>(
            dataTreeBranches,
            ...(this.dataTreeBranch?.branchParents ?? [])
          )
        )
      )
      .subscribe((dataTreeBranches: IDataTreeBranch[]) => (this.dataTreeBranchesSharedCollection = dataTreeBranches));
  }
}
