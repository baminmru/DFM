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
import { IDataField } from 'app/entities/data-field/data-field.model';
import { DataFieldService } from 'app/entities/data-field/service/data-field.service';
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

  dataFieldsSharedCollection: IDataField[] = [];

  editForm: DataTreeLeafFormGroup = this.dataTreeLeafFormService.createDataTreeLeafFormGroup();

  constructor(
    protected dataTreeLeafService: DataTreeLeafService,
    protected dataTreeLeafFormService: DataTreeLeafFormService,
    protected dataFieldService: DataFieldService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareDataField = (o1: IDataField | null, o2: IDataField | null): boolean => this.dataFieldService.compareDataField(o1, o2);

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

    this.dataFieldsSharedCollection = this.dataFieldService.addDataFieldToCollectionIfMissing<IDataField>(
      this.dataFieldsSharedCollection,
      ...(dataTreeLeaf.leafToFields ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.dataFieldService
      .query()
      .pipe(map((res: HttpResponse<IDataField[]>) => res.body ?? []))
      .pipe(
        map((dataFields: IDataField[]) =>
          this.dataFieldService.addDataFieldToCollectionIfMissing<IDataField>(dataFields, ...(this.dataTreeLeaf?.leafToFields ?? []))
        )
      )
      .subscribe((dataFields: IDataField[]) => (this.dataFieldsSharedCollection = dataFields));
  }
}
