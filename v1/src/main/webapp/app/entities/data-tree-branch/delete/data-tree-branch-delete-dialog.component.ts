import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { IDataTreeBranch } from '../data-tree-branch.model';
import { DataTreeBranchService } from '../service/data-tree-branch.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  standalone: true,
  templateUrl: './data-tree-branch-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DataTreeBranchDeleteDialogComponent {
  dataTreeBranch?: IDataTreeBranch;

  constructor(protected dataTreeBranchService: DataTreeBranchService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dataTreeBranchService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
