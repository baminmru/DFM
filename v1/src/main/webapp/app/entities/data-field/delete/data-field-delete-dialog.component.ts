import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { IDataField } from '../data-field.model';
import { DataFieldService } from '../service/data-field.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  standalone: true,
  templateUrl: './data-field-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DataFieldDeleteDialogComponent {
  dataField?: IDataField;

  constructor(protected dataFieldService: DataFieldService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dataFieldService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
