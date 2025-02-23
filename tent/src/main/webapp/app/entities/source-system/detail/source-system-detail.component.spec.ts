import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SourceSystemDetailComponent } from './source-system-detail.component';

describe('SourceSystem Management Detail Component', () => {
  let comp: SourceSystemDetailComponent;
  let fixture: ComponentFixture<SourceSystemDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SourceSystemDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: SourceSystemDetailComponent,
              resolve: { sourceSystem: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SourceSystemDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SourceSystemDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load sourceSystem on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SourceSystemDetailComponent);

      // THEN
      expect(instance.sourceSystem()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
