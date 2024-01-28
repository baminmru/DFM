import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IDataTreeBranch } from '../data-tree-branch.model';

@Component({
  standalone: true,
  selector: 'jhi-data-tree-branch-detail',
  templateUrl: './data-tree-branch-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DataTreeBranchDetailComponent {
  @Input() dataTreeBranch: IDataTreeBranch | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
