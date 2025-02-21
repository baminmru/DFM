import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IRequestConfig } from '../request-config.model';

@Component({
  standalone: true,
  selector: 'jhi-request-config-detail',
  templateUrl: './request-config-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class RequestConfigDetailComponent {
  requestConfig = input<IRequestConfig | null>(null);

  previousState(): void {
    window.history.back();
  }
}
