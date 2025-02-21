import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRequestInfo } from '../request-info.model';
import { RequestInfoService } from '../service/request-info.service';

@Component({
  standalone: true,
  templateUrl: './request-info-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RequestInfoDeleteDialogComponent {
  requestInfo?: IRequestInfo;

  protected requestInfoService = inject(RequestInfoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.requestInfoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
