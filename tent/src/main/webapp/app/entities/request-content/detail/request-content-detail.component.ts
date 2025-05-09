import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IRequestContent } from '../request-content.model';

@Component({
  standalone: true,
  selector: 'jhi-request-content-detail',
  templateUrl: './request-content-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class RequestContentDetailComponent {
  requestContent = input<IRequestContent | null>(null);

  previousState(): void {
    window.history.back();
  }
}
