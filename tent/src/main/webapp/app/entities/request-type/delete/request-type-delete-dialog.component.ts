import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRequestType } from '../request-type.model';
import { RequestTypeService } from '../service/request-type.service';

@Component({
  standalone: true,
  templateUrl: './request-type-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RequestTypeDeleteDialogComponent {
  requestType?: IRequestType;

  protected requestTypeService = inject(RequestTypeService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.requestTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
