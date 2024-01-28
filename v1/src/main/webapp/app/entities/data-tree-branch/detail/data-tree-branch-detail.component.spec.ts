import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DataTreeBranchDetailComponent } from './data-tree-branch-detail.component';

describe('DataTreeBranch Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DataTreeBranchDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DataTreeBranchDetailComponent,
              resolve: { dataTreeBranch: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(DataTreeBranchDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load dataTreeBranch on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DataTreeBranchDetailComponent);

      // THEN
      expect(instance.dataTreeBranch).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
