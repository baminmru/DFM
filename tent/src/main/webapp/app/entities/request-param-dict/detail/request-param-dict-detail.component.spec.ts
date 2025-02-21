import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { RequestParamDictDetailComponent } from './request-param-dict-detail.component';

describe('RequestParamDict Management Detail Component', () => {
  let comp: RequestParamDictDetailComponent;
  let fixture: ComponentFixture<RequestParamDictDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RequestParamDictDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: RequestParamDictDetailComponent,
              resolve: { requestParamDict: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(RequestParamDictDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RequestParamDictDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load requestParamDict on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', RequestParamDictDetailComponent);

      // THEN
      expect(instance.requestParamDict()).toEqual(expect.objectContaining({ id: 123 }));
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
