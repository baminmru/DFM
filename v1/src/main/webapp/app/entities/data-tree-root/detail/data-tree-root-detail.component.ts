import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IDataTreeRoot } from '../data-tree-root.model';

@Component({
  standalone: true,
  selector: 'jhi-data-tree-root-detail',
  templateUrl: './data-tree-root-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DataTreeRootDetailComponent {
  @Input() dataTreeRoot: IDataTreeRoot | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
