import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DataFieldDetailComponent } from './data-field-detail.component';

describe('DataField Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DataFieldDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DataFieldDetailComponent,
              resolve: { dataField: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(DataFieldDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load dataField on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DataFieldDetailComponent);

      // THEN
      expect(instance.dataField).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
