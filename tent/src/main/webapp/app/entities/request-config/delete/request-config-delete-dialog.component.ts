import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRequestConfig } from '../request-config.model';
import { RequestConfigService } from '../service/request-config.service';

@Component({
  standalone: true,
  templateUrl: './request-config-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RequestConfigDeleteDialogComponent {
  requestConfig?: IRequestConfig;

  protected requestConfigService = inject(RequestConfigService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.requestConfigService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
