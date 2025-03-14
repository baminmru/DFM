import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ISourceSystem } from '../source-system.model';

@Component({
  standalone: true,
  selector: 'jhi-source-system-detail',
  templateUrl: './source-system-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class SourceSystemDetailComponent {
  sourceSystem = input<ISourceSystem | null>(null);

  previousState(): void {
    window.history.back();
  }
}
