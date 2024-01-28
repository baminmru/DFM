import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { DataFieldFormService, DataFieldFormGroup } from './data-field-form.service';
import { IDataField } from '../data-field.model';
import { DataFieldService } from '../service/data-field.service';
import { IDataTreeRoot } from 'app/entities/data-tree-root/data-tree-root.model';
import { DataTreeRootService } from 'app/entities/data-tree-root/service/data-tree-root.service';
import { InputTypeEnum } from 'app/entities/enumerations/input-type-enum.model';
import { FieldTypeEnum } from 'app/entities/enumerations/field-type-enum.model';

@Component({
  standalone: true,
  selector: 'jhi-data-field-update',
  templateUrl: './data-field-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DataFieldUpdateComponent implements OnInit {
  isSaving = false;
  dataField: IDataField | null = null;
  inputTypeEnumValues = Object.keys(InputTypeEnum);
  fieldTypeEnumValues = Object.keys(FieldTypeEnum);

  dataTreeRootsSharedCollection: IDataTreeRoot[] = [];

  editForm: DataFieldFormGroup = this.dataFieldFormService.createDataFieldFormGroup();

  constructor(
    protected dataFieldService: DataFieldService,
    protected dataFieldFormService: DataFieldFormService,
    protected dataTreeRootService: DataTreeRootService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDataTreeRoot = (o1: IDataTreeRoot | null, o2: IDataTreeRoot | null): boolean =>
    this.dataTreeRootService.compareDataTreeRoot(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dataField }) => {
      this.dataField = dataField;
      if (dataField) {
        this.updateForm(dataField);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dataField = this.dataFieldFormService.getDataField(this.editForm);
    if (dataField.id !== null) {
      this.subscribeToSaveResponse(this.dataFieldService.update(dataField));
    } else {
      this.subscribeToSaveResponse(this.dataFieldService.create(dataField));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataField>>): void {
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

  protected updateForm(dataField: IDataField): void {
    this.dataField = dataField;
    this.dataFieldFormService.resetForm(this.editForm, dataField);

    this.dataTreeRootsSharedCollection = this.dataTreeRootService.addDataTreeRootToCollectionIfMissing<IDataTreeRoot>(
      this.dataTreeRootsSharedCollection,
      dataField.refToRoot
    );
  }

  protected loadRelationshipsOptions(): void {
    this.dataTreeRootService
      .query()
      .pipe(map((res: HttpResponse<IDataTreeRoot[]>) => res.body ?? []))
      .pipe(
        map((dataTreeRoots: IDataTreeRoot[]) =>
          this.dataTreeRootService.addDataTreeRootToCollectionIfMissing<IDataTreeRoot>(dataTreeRoots, this.dataField?.refToRoot)
        )
      )
      .subscribe((dataTreeRoots: IDataTreeRoot[]) => (this.dataTreeRootsSharedCollection = dataTreeRoots));
  }
}
