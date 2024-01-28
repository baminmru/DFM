import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IDataTreeLeaf } from '../data-tree-leaf.model';

@Component({
  standalone: true,
  selector: 'jhi-data-tree-leaf-detail',
  templateUrl: './data-tree-leaf-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class DataTreeLeafDetailComponent {
  @Input() dataTreeLeaf: IDataTreeLeaf | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
