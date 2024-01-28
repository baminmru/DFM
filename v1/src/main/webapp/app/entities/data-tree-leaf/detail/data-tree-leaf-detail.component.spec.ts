import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DataTreeLeafDetailComponent } from './data-tree-leaf-detail.component';

describe('DataTreeLeaf Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DataTreeLeafDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DataTreeLeafDetailComponent,
              resolve: { dataTreeLeaf: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(DataTreeLeafDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load dataTreeLeaf on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DataTreeLeafDetailComponent);

      // THEN
      expect(instance.dataTreeLeaf).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
