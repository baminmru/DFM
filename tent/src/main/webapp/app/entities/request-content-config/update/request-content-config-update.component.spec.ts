import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IRequestConfig } from 'app/entities/request-config/request-config.model';
import { RequestConfigService } from 'app/entities/request-config/service/request-config.service';
import { IRequestParamDict } from 'app/entities/request-param-dict/request-param-dict.model';
import { RequestParamDictService } from 'app/entities/request-param-dict/service/request-param-dict.service';
import { IRequestContentConfig } from '../request-content-config.model';
import { RequestContentConfigService } from '../service/request-content-config.service';
import { RequestContentConfigFormService } from './request-content-config-form.service';

import { RequestContentConfigUpdateComponent } from './request-content-config-update.component';

describe('RequestContentConfig Management Update Component', () => {
  let comp: RequestContentConfigUpdateComponent;
  let fixture: ComponentFixture<RequestContentConfigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let requestContentConfigFormService: RequestContentConfigFormService;
  let requestContentConfigService: RequestContentConfigService;
  let requestConfigService: RequestConfigService;
  let requestParamDictService: RequestParamDictService;

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
    requestConfigService = TestBed.inject(RequestConfigService);
    requestParamDictService = TestBed.inject(RequestParamDictService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RequestConfig query and add missing value', () => {
      const requestContentConfig: IRequestContentConfig = { id: 456 };
      const requestConfigId: IRequestConfig = { id: 6035 };
      requestContentConfig.requestConfigId = requestConfigId;

      const requestConfigCollection: IRequestConfig[] = [{ id: 28802 }];
      jest.spyOn(requestConfigService, 'query').mockReturnValue(of(new HttpResponse({ body: requestConfigCollection })));
      const additionalRequestConfigs = [requestConfigId];
      const expectedCollection: IRequestConfig[] = [...additionalRequestConfigs, ...requestConfigCollection];
      jest.spyOn(requestConfigService, 'addRequestConfigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ requestContentConfig });
      comp.ngOnInit();

      expect(requestConfigService.query).toHaveBeenCalled();
      expect(requestConfigService.addRequestConfigToCollectionIfMissing).toHaveBeenCalledWith(
        requestConfigCollection,
        ...additionalRequestConfigs.map(expect.objectContaining),
      );
      expect(comp.requestConfigsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call RequestParamDict query and add missing value', () => {
      const requestContentConfig: IRequestContentConfig = { id: 456 };
      const parameter: IRequestParamDict = { id: 21938 };
      requestContentConfig.parameter = parameter;

      const requestParamDictCollection: IRequestParamDict[] = [{ id: 10650 }];
      jest.spyOn(requestParamDictService, 'query').mockReturnValue(of(new HttpResponse({ body: requestParamDictCollection })));
      const additionalRequestParamDicts = [parameter];
      const expectedCollection: IRequestParamDict[] = [...additionalRequestParamDicts, ...requestParamDictCollection];
      jest.spyOn(requestParamDictService, 'addRequestParamDictToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ requestContentConfig });
      comp.ngOnInit();

      expect(requestParamDictService.query).toHaveBeenCalled();
      expect(requestParamDictService.addRequestParamDictToCollectionIfMissing).toHaveBeenCalledWith(
        requestParamDictCollection,
        ...additionalRequestParamDicts.map(expect.objectContaining),
      );
      expect(comp.requestParamDictsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const requestContentConfig: IRequestContentConfig = { id: 456 };
      const requestConfigId: IRequestConfig = { id: 31201 };
      requestContentConfig.requestConfigId = requestConfigId;
      const parameter: IRequestParamDict = { id: 20334 };
      requestContentConfig.parameter = parameter;

      activatedRoute.data = of({ requestContentConfig });
      comp.ngOnInit();

      expect(comp.requestConfigsSharedCollection).toContain(requestConfigId);
      expect(comp.requestParamDictsSharedCollection).toContain(parameter);
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

  describe('Compare relationships', () => {
    describe('compareRequestConfig', () => {
      it('Should forward to requestConfigService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(requestConfigService, 'compareRequestConfig');
        comp.compareRequestConfig(entity, entity2);
        expect(requestConfigService.compareRequestConfig).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareRequestParamDict', () => {
      it('Should forward to requestParamDictService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(requestParamDictService, 'compareRequestParamDict');
        comp.compareRequestParamDict(entity, entity2);
        expect(requestParamDictService.compareRequestParamDict).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
