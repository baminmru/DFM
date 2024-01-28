import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { IDataForest } from '../data-forest.model';
import { DataForestService } from '../service/data-forest.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  standalone: true,
  templateUrl: './data-forest-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DataForestDeleteDialogComponent {
  dataForest?: IDataForest;

  constructor(protected dataForestService: DataForestService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dataForestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
