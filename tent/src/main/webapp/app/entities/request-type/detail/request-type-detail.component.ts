import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IRequestType } from '../request-type.model';

@Component({
  standalone: true,
  selector: 'jhi-request-type-detail',
  templateUrl: './request-type-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class RequestTypeDetailComponent {
  requestType = input<IRequestType | null>(null);

  previousState(): void {
    window.history.back();
  }
}
