import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DataTreeRootDetailComponent } from './data-tree-root-detail.component';

describe('DataTreeRoot Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DataTreeRootDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DataTreeRootDetailComponent,
              resolve: { dataTreeRoot: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(DataTreeRootDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load dataTreeRoot on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DataTreeRootDetailComponent);

      // THEN
      expect(instance.dataTreeRoot).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
