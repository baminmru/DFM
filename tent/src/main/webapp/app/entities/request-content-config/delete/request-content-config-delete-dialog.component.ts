import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRequestContentConfig } from '../request-content-config.model';
import { RequestContentConfigService } from '../service/request-content-config.service';

@Component({
  standalone: true,
  templateUrl: './request-content-config-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RequestContentConfigDeleteDialogComponent {
  requestContentConfig?: IRequestContentConfig;

  protected requestContentConfigService = inject(RequestContentConfigService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.requestContentConfigService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
