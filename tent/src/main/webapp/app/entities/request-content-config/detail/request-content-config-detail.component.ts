import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IRequestContentConfig } from '../request-content-config.model';

@Component({
  standalone: true,
  selector: 'jhi-request-content-config-detail',
  templateUrl: './request-content-config-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class RequestContentConfigDetailComponent {
  requestContentConfig = input<IRequestContentConfig | null>(null);

  previousState(): void {
    window.history.back();
  }
}
