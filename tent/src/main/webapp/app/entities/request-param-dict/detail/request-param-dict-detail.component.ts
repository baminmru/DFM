import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IRequestParamDict } from '../request-param-dict.model';

@Component({
  standalone: true,
  selector: 'jhi-request-param-dict-detail',
  templateUrl: './request-param-dict-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class RequestParamDictDetailComponent {
  requestParamDict = input<IRequestParamDict | null>(null);

  previousState(): void {
    window.history.back();
  }
}
