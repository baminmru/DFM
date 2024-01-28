import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DataForestDetailComponent } from './data-forest-detail.component';

describe('DataForest Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DataForestDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DataForestDetailComponent,
              resolve: { dataForest: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(DataForestDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load dataForest on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DataForestDetailComponent);

      // THEN
      expect(instance.dataForest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
