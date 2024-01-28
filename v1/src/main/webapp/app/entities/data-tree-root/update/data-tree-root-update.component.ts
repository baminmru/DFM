import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { DataTreeRootFormService, DataTreeRootFormGroup } from './data-tree-root-form.service';
import { IDataTreeRoot } from '../data-tree-root.model';
import { DataTreeRootService } from '../service/data-tree-root.service';
import { IDataTreeBranch } from 'app/entities/data-tree-branch/data-tree-branch.model';
import { DataTreeBranchService } from 'app/entities/data-tree-branch/service/data-tree-branch.service';
import { IDataTreeRootToField } from 'app/entities/data-tree-root-to-field/data-tree-root-to-field.model';
import { DataTreeRootToFieldService } from 'app/entities/data-tree-root-to-field/service/data-tree-root-to-field.service';
import { StereoTypeEnum } from 'app/entities/enumerations/stereo-type-enum.model';

@Component({
  standalone: true,
  selector: 'jhi-data-tree-root-update',
  templateUrl: './data-tree-root-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DataTreeRootUpdateComponent implements OnInit {
  isSaving = false;
  dataTreeRoot: IDataTreeRoot | null = null;
  stereoTypeEnumValues = Object.keys(StereoTypeEnum);

  dataTreeBranchesSharedCollection: IDataTreeBranch[] = [];
  dataTreeRootToFieldsSharedCollection: IDataTreeRootToField[] = [];

  editForm: DataTreeRootFormGroup = this.dataTreeRootFormService.createDataTreeRootFormGroup();

  constructor(
    protected dataTreeRootService: DataTreeRootService,
    protected dataTreeRootFormService: DataTreeRootFormService,
    protected dataTreeBranchService: DataTreeBranchService,
    protected dataTreeRootToFieldService: DataTreeRootToFieldService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDataTreeBranch = (o1: IDataTreeBranch | null, o2: IDataTreeBranch | null): boolean =>
    this.dataTreeBranchService.compareDataTreeBranch(o1, o2);

  compareDataTreeRootToField = (o1: IDataTreeRootToField | null, o2: IDataTreeRootToField | null): boolean =>
    this.dataTreeRootToFieldService.compareDataTreeRootToField(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dataTreeRoot }) => {
      this.dataTreeRoot = dataTreeRoot;
      if (dataTreeRoot) {
        this.updateForm(dataTreeRoot);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dataTreeRoot = this.dataTreeRootFormService.getDataTreeRoot(this.editForm);
    if (dataTreeRoot.id !== null) {
      this.subscribeToSaveResponse(this.dataTreeRootService.update(dataTreeRoot));
    } else {
      this.subscribeToSaveResponse(this.dataTreeRootService.create(dataTreeRoot));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataTreeRoot>>): void {
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

  protected updateForm(dataTreeRoot: IDataTreeRoot): void {
    this.dataTreeRoot = dataTreeRoot;
    this.dataTreeRootFormService.resetForm(this.editForm, dataTreeRoot);

    this.dataTreeBranchesSharedCollection = this.dataTreeBranchService.addDataTreeBranchToCollectionIfMissing<IDataTreeBranch>(
      this.dataTreeBranchesSharedCollection,
      dataTreeRoot.dataTreeBranch
    );
    this.dataTreeRootToFieldsSharedCollection =
      this.dataTreeRootToFieldService.addDataTreeRootToFieldToCollectionIfMissing<IDataTreeRootToField>(
        this.dataTreeRootToFieldsSharedCollection,
        dataTreeRoot.rootToField
      );
  }

  protected loadRelationshipsOptions(): void {
    this.dataTreeBranchService
      .query()
      .pipe(map((res: HttpResponse<IDataTreeBranch[]>) => res.body ?? []))
      .pipe(
        map((dataTreeBranches: IDataTreeBranch[]) =>
          this.dataTreeBranchService.addDataTreeBranchToCollectionIfMissing<IDataTreeBranch>(
            dataTreeBranches,
            this.dataTreeRoot?.dataTreeBranch
          )
        )
      )
      .subscribe((dataTreeBranches: IDataTreeBranch[]) => (this.dataTreeBranchesSharedCollection = dataTreeBranches));

    this.dataTreeRootToFieldService
      .query()
      .pipe(map((res: HttpResponse<IDataTreeRootToField[]>) => res.body ?? []))
      .pipe(
        map((dataTreeRootToFields: IDataTreeRootToField[]) =>
          this.dataTreeRootToFieldService.addDataTreeRootToFieldToCollectionIfMissing<IDataTreeRootToField>(
            dataTreeRootToFields,
            this.dataTreeRoot?.rootToField
          )
        )
      )
      .subscribe((dataTreeRootToFields: IDataTreeRootToField[]) => (this.dataTreeRootToFieldsSharedCollection = dataTreeRootToFields));
  }
}
