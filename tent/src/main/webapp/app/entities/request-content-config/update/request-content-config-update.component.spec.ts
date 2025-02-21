import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { RequestContentConfigService } from '../service/request-content-config.service';
import { IRequestContentConfig } from '../request-content-config.model';
import { RequestContentConfigFormService } from './request-content-config-form.service';

import { RequestContentConfigUpdateComponent } from './request-content-config-update.component';

describe('RequestContentConfig Management Update Component', () => {
  let comp: RequestContentConfigUpdateComponent;
  let fixture: ComponentFixture<RequestContentConfigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let requestContentConfigFormService: RequestContentConfigFormService;
  let requestContentConfigService: RequestContentConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RequestContentConfigUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(RequestContentConfigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RequestContentConfigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    requestContentConfigFormService = TestBed.inject(RequestContentConfigFormService);
    requestContentConfigService = TestBed.inject(RequestContentConfigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const requestContentConfig: IRequestContentConfig = { id: 456 };

      activatedRoute.data = of({ requestContentConfig });
      comp.ngOnInit();

      expect(comp.requestContentConfig).toEqual(requestContentConfig);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequestContentConfig>>();
      const requestContentConfig = { id: 123 };
      jest.spyOn(requestContentConfigFormService, 'getRequestContentConfig').mockReturnValue(requestContentConfig);
      jest.spyOn(requestContentConfigService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestContentConfig });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requestContentConfig }));
      saveSubject.complete();

      // THEN
      expect(requestContentConfigFormService.getRequestContentConfig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(requestContentConfigService.update).toHaveBeenCalledWith(expect.objectContaining(requestContentConfig));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequestContentConfig>>();
      const requestContentConfig = { id: 123 };
      jest.spyOn(requestContentConfigFormService, 'getRequestContentConfig').mockReturnValue({ id: null });
      jest.spyOn(requestContentConfigService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestContentConfig: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requestContentConfig }));
      saveSubject.complete();

      // THEN
      expect(requestContentConfigFormService.getRequestContentConfig).toHaveBeenCalled();
      expect(requestContentConfigService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequestContentConfig>>();
      const requestContentConfig = { id: 123 };
      jest.spyOn(requestContentConfigService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestContentConfig });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(requestContentConfigService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
