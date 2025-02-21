import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IRequestInfo } from '../request-info.model';

@Component({
  standalone: true,
  selector: 'jhi-request-info-detail',
  templateUrl: './request-info-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class RequestInfoDetailComponent {
  requestInfo = input<IRequestInfo | null>(null);

  previousState(): void {
    window.history.back();
  }
}
