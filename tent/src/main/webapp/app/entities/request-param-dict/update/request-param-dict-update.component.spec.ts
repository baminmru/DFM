import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { RequestParamDictService } from '../service/request-param-dict.service';
import { IRequestParamDict } from '../request-param-dict.model';
import { RequestParamDictFormService } from './request-param-dict-form.service';

import { RequestParamDictUpdateComponent } from './request-param-dict-update.component';

describe('RequestParamDict Management Update Component', () => {
  let comp: RequestParamDictUpdateComponent;
  let fixture: ComponentFixture<RequestParamDictUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let requestParamDictFormService: RequestParamDictFormService;
  let requestParamDictService: RequestParamDictService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RequestParamDictUpdateComponent],
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
      .overrideTemplate(RequestParamDictUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RequestParamDictUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    requestParamDictFormService = TestBed.inject(RequestParamDictFormService);
    requestParamDictService = TestBed.inject(RequestParamDictService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const requestParamDict: IRequestParamDict = { id: 456 };

      activatedRoute.data = of({ requestParamDict });
      comp.ngOnInit();

      expect(comp.requestParamDict).toEqual(requestParamDict);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequestParamDict>>();
      const requestParamDict = { id: 123 };
      jest.spyOn(requestParamDictFormService, 'getRequestParamDict').mockReturnValue(requestParamDict);
      jest.spyOn(requestParamDictService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestParamDict });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requestParamDict }));
      saveSubject.complete();

      // THEN
      expect(requestParamDictFormService.getRequestParamDict).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(requestParamDictService.update).toHaveBeenCalledWith(expect.objectContaining(requestParamDict));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequestParamDict>>();
      const requestParamDict = { id: 123 };
      jest.spyOn(requestParamDictFormService, 'getRequestParamDict').mockReturnValue({ id: null });
      jest.spyOn(requestParamDictService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestParamDict: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requestParamDict }));
      saveSubject.complete();

      // THEN
      expect(requestParamDictFormService.getRequestParamDict).toHaveBeenCalled();
      expect(requestParamDictService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequestParamDict>>();
      const requestParamDict = { id: 123 };
      jest.spyOn(requestParamDictService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestParamDict });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(requestParamDictService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
