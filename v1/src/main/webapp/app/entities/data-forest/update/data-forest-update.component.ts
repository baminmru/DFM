import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { DataForestFormService, DataForestFormGroup } from './data-forest-form.service';
import { IDataForest } from '../data-forest.model';
import { DataForestService } from '../service/data-forest.service';
import { IDataTreeRoot } from 'app/entities/data-tree-root/data-tree-root.model';
import { DataTreeRootService } from 'app/entities/data-tree-root/service/data-tree-root.service';

@Component({
  standalone: true,
  selector: 'jhi-data-forest-update',
  templateUrl: './data-forest-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DataForestUpdateComponent implements OnInit {
  isSaving = false;
  dataForest: IDataForest | null = null;

  dataTreeRootsSharedCollection: IDataTreeRoot[] = [];

  editForm: DataForestFormGroup = this.dataForestFormService.createDataForestFormGroup();

  constructor(
    protected dataForestService: DataForestService,
    protected dataForestFormService: DataForestFormService,
    protected dataTreeRootService: DataTreeRootService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDataTreeRoot = (o1: IDataTreeRoot | null, o2: IDataTreeRoot | null): boolean =>
    this.dataTreeRootService.compareDataTreeRoot(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dataForest }) => {
      this.dataForest = dataForest;
      if (dataForest) {
        this.updateForm(dataForest);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dataForest = this.dataForestFormService.getDataForest(this.editForm);
    if (dataForest.id !== null) {
      this.subscribeToSaveResponse(this.dataForestService.update(dataForest));
    } else {
      this.subscribeToSaveResponse(this.dataForestService.create(dataForest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataForest>>): void {
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

  protected updateForm(dataForest: IDataForest): void {
    this.dataForest = dataForest;
    this.dataForestFormService.resetForm(this.editForm, dataForest);

    this.dataTreeRootsSharedCollection = this.dataTreeRootService.addDataTreeRootToCollectionIfMissing<IDataTreeRoot>(
      this.dataTreeRootsSharedCollection,
      dataForest.forestTrees
    );
  }

  protected loadRelationshipsOptions(): void {
    this.dataTreeRootService
      .query()
      .pipe(map((res: HttpResponse<IDataTreeRoot[]>) => res.body ?? []))
      .pipe(
        map((dataTreeRoots: IDataTreeRoot[]) =>
          this.dataTreeRootService.addDataTreeRootToCollectionIfMissing<IDataTreeRoot>(dataTreeRoots, this.dataForest?.forestTrees)
        )
      )
      .subscribe((dataTreeRoots: IDataTreeRoot[]) => (this.dataTreeRootsSharedCollection = dataTreeRoots));
  }
}
