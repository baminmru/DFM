import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { RequestContentConfigDetailComponent } from './request-content-config-detail.component';

describe('RequestContentConfig Management Detail Component', () => {
  let comp: RequestContentConfigDetailComponent;
  let fixture: ComponentFixture<RequestContentConfigDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RequestContentConfigDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: RequestContentConfigDetailComponent,
              resolve: { requestContentConfig: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(RequestContentConfigDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RequestContentConfigDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load requestContentConfig on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', RequestContentConfigDetailComponent);

      // THEN
      expect(instance.requestContentConfig()).toEqual(expect.objectContaining({ id: 123 }));
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
