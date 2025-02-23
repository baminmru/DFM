import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ISourceSystem } from '../source-system.model';
import { SourceSystemService } from '../service/source-system.service';
import { SourceSystemFormService, SourceSystemFormGroup } from './source-system-form.service';

@Component({
  standalone: true,
  selector: 'jhi-source-system-update',
  templateUrl: './source-system-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class SourceSystemUpdateComponent implements OnInit {
  isSaving = false;
  sourceSystem: ISourceSystem | null = null;

  protected sourceSystemService = inject(SourceSystemService);
  protected sourceSystemFormService = inject(SourceSystemFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: SourceSystemFormGroup = this.sourceSystemFormService.createSourceSystemFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sourceSystem }) => {
      this.sourceSystem = sourceSystem;
      if (sourceSystem) {
        this.updateForm(sourceSystem);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sourceSystem = this.sourceSystemFormService.getSourceSystem(this.editForm);
    if (sourceSystem.id !== null) {
      this.subscribeToSaveResponse(this.sourceSystemService.update(sourceSystem));
    } else {
      this.subscribeToSaveResponse(this.sourceSystemService.create(sourceSystem));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISourceSystem>>): void {
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

  protected updateForm(sourceSystem: ISourceSystem): void {
    this.sourceSystem = sourceSystem;
    this.sourceSystemFormService.resetForm(this.editForm, sourceSystem);
  }
}
