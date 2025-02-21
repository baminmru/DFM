import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IRequestParamDict } from '../request-param-dict.model';
import { RequestParamDictService } from '../service/request-param-dict.service';

@Component({
  standalone: true,
  templateUrl: './request-param-dict-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class RequestParamDictDeleteDialogComponent {
  requestParamDict?: IRequestParamDict;

  protected requestParamDictService = inject(RequestParamDictService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.requestParamDictService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
