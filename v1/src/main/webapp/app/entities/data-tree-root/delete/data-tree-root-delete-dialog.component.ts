import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { IDataTreeRoot } from '../data-tree-root.model';
import { DataTreeRootService } from '../service/data-tree-root.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  standalone: true,
  templateUrl: './data-tree-root-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DataTreeRootDeleteDialogComponent {
  dataTreeRoot?: IDataTreeRoot;

  constructor(protected dataTreeRootService: DataTreeRootService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dataTreeRootService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
