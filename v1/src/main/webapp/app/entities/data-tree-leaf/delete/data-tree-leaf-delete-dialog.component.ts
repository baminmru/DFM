import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { IDataTreeLeaf } from '../data-tree-leaf.model';
import { DataTreeLeafService } from '../service/data-tree-leaf.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  standalone: true,
  templateUrl: './data-tree-leaf-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DataTreeLeafDeleteDialogComponent {
  dataTreeLeaf?: IDataTreeLeaf;

  constructor(protected dataTreeLeafService: DataTreeLeafService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dataTreeLeafService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
