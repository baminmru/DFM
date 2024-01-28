import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { DataTreeLeafFormService, DataTreeLeafFormGroup } from './data-tree-leaf-form.service';
import { IDataTreeLeaf } from '../data-tree-leaf.model';
import { DataTreeLeafService } from '../service/data-tree-leaf.service';
import { IDataTreeLeafToField } from 'app/entities/data-tree-leaf-to-field/data-tree-leaf-to-field.model';
import { DataTreeLeafToFieldService } from 'app/entities/data-tree-leaf-to-field/service/data-tree-leaf-to-field.service';
import { StereoTypeEnum } from 'app/entities/enumerations/stereo-type-enum.model';

@Component({
  standalone: true,
  selector: 'jhi-data-tree-leaf-update',
  templateUrl: './data-tree-leaf-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DataTreeLeafUpdateComponent implements OnInit {
  isSaving = false;
  dataTreeLeaf: IDataTreeLeaf | null = null;
  stereoTypeEnumValues = Object.keys(StereoTypeEnum);

  dataTreeLeafToFieldsSharedCollection: IDataTreeLeafToField[] = [];

  editForm: DataTreeLeafFormGroup = this.dataTreeLeafFormService.createDataTreeLeafFormGroup();

  constructor(
    protected dataTreeLeafService: DataTreeLeafService,
    protected dataTreeLeafFormService: DataTreeLeafFormService,
    protected dataTreeLeafToFieldService: DataTreeLeafToFieldService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDataTreeLeafToField = (o1: IDataTreeLeafToField | null, o2: IDataTreeLeafToField | null): boolean =>
    this.dataTreeLeafToFieldService.compareDataTreeLeafToField(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dataTreeLeaf }) => {
      this.dataTreeLeaf = dataTreeLeaf;
      if (dataTreeLeaf) {
        this.updateForm(dataTreeLeaf);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dataTreeLeaf = this.dataTreeLeafFormService.getDataTreeLeaf(this.editForm);
    if (dataTreeLeaf.id !== null) {
      this.subscribeToSaveResponse(this.dataTreeLeafService.update(dataTreeLeaf));
    } else {
      this.subscribeToSaveResponse(this.dataTreeLeafService.create(dataTreeLeaf));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataTreeLeaf>>): void {
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

  protected updateForm(dataTreeLeaf: IDataTreeLeaf): void {
    this.dataTreeLeaf = dataTreeLeaf;
    this.dataTreeLeafFormService.resetForm(this.editForm, dataTreeLeaf);

    this.dataTreeLeafToFieldsSharedCollection =
      this.dataTreeLeafToFieldService.addDataTreeLeafToFieldToCollectionIfMissing<IDataTreeLeafToField>(
        this.dataTreeLeafToFieldsSharedCollection,
        dataTreeLeaf.leafToField
      );
  }

  protected loadRelationshipsOptions(): void {
    this.dataTreeLeafToFieldService
      .query()
      .pipe(map((res: HttpResponse<IDataTreeLeafToField[]>) => res.body ?? []))
      .pipe(
        map((dataTreeLeafToFields: IDataTreeLeafToField[]) =>
          this.dataTreeLeafToFieldService.addDataTreeLeafToFieldToCollectionIfMissing<IDataTreeLeafToField>(
            dataTreeLeafToFields,
            this.dataTreeLeaf?.leafToField
          )
        )
      )
      .subscribe((dataTreeLeafToFields: IDataTreeLeafToField[]) => (this.dataTreeLeafToFieldsSharedCollection = dataTreeLeafToFields));
  }
}
