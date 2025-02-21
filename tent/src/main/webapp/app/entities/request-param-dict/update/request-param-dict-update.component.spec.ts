import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IRequestContentConfig } from 'app/entities/request-content-config/request-content-config.model';
import { RequestContentConfigService } from 'app/entities/request-content-config/service/request-content-config.service';
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
  let requestContentConfigService: RequestContentConfigService;

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
    requestContentConfigService = TestBed.inject(RequestContentConfigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RequestContentConfig query and add missing value', () => {
      const requestParamDict: IRequestParamDict = { id: 456 };
      const requestContentConfig: IRequestContentConfig = { id: 2558 };
      requestParamDict.requestContentConfig = requestContentConfig;

      const requestContentConfigCollection: IRequestContentConfig[] = [{ id: 29758 }];
      jest.spyOn(requestContentConfigService, 'query').mockReturnValue(of(new HttpResponse({ body: requestContentConfigCollection })));
      const additionalRequestContentConfigs = [requestContentConfig];
      const expectedCollection: IRequestContentConfig[] = [...additionalRequestContentConfigs, ...requestContentConfigCollection];
      jest.spyOn(requestContentConfigService, 'addRequestContentConfigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ requestParamDict });
      comp.ngOnInit();

      expect(requestContentConfigService.query).toHaveBeenCalled();
      expect(requestContentConfigService.addRequestContentConfigToCollectionIfMissing).toHaveBeenCalledWith(
        requestContentConfigCollection,
        ...additionalRequestContentConfigs.map(expect.objectContaining),
      );
      expect(comp.requestContentConfigsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const requestParamDict: IRequestParamDict = { id: 456 };
      const requestContentConfig: IRequestContentConfig = { id: 28088 };
      requestParamDict.requestContentConfig = requestContentConfig;

      activatedRoute.data = of({ requestParamDict });
      comp.ngOnInit();

      expect(comp.requestContentConfigsSharedCollection).toContain(requestContentConfig);
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

  describe('Compare relationships', () => {
    describe('compareRequestContentConfig', () => {
      it('Should forward to requestContentConfigService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(requestContentConfigService, 'compareRequestContentConfig');
        comp.compareRequestContentConfig(entity, entity2);
        expect(requestContentConfigService.compareRequestContentConfig).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
