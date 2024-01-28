import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { DataFieldFormService, DataFieldFormGroup } from './data-field-form.service';
import { IDataField } from '../data-field.model';
import { DataFieldService } from '../service/data-field.service';
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

  editForm: DataFieldFormGroup = this.dataFieldFormService.createDataFieldFormGroup();

  constructor(
    protected dataFieldService: DataFieldService,
    protected dataFieldFormService: DataFieldFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dataField }) => {
      this.dataField = dataField;
      if (dataField) {
        this.updateForm(dataField);
      }
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
  }
}
