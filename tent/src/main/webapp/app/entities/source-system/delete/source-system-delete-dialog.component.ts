import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISourceSystem } from '../source-system.model';
import { SourceSystemService } from '../service/source-system.service';

@Component({
  standalone: true,
  templateUrl: './source-system-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SourceSystemDeleteDialogComponent {
  sourceSystem?: ISourceSystem;

  protected sourceSystemService = inject(SourceSystemService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sourceSystemService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
