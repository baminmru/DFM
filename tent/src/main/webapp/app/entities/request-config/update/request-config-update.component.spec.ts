import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IRequestContentConfig } from 'app/entities/request-content-config/request-content-config.model';
import { RequestContentConfigService } from 'app/entities/request-content-config/service/request-content-config.service';
import { RequestConfigService } from '../service/request-config.service';
import { IRequestConfig } from '../request-config.model';
import { RequestConfigFormService } from './request-config-form.service';

import { RequestConfigUpdateComponent } from './request-config-update.component';

describe('RequestConfig Management Update Component', () => {
  let comp: RequestConfigUpdateComponent;
  let fixture: ComponentFixture<RequestConfigUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let requestConfigFormService: RequestConfigFormService;
  let requestConfigService: RequestConfigService;
  let requestContentConfigService: RequestContentConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RequestConfigUpdateComponent],
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
      .overrideTemplate(RequestConfigUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RequestConfigUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    requestConfigFormService = TestBed.inject(RequestConfigFormService);
    requestConfigService = TestBed.inject(RequestConfigService);
    requestContentConfigService = TestBed.inject(RequestContentConfigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RequestContentConfig query and add missing value', () => {
      const requestConfig: IRequestConfig = { id: 456 };
      const requestContentConfig: IRequestContentConfig = { id: 26647 };
      requestConfig.requestContentConfig = requestContentConfig;

      const requestContentConfigCollection: IRequestContentConfig[] = [{ id: 29644 }];
      jest.spyOn(requestContentConfigService, 'query').mockReturnValue(of(new HttpResponse({ body: requestContentConfigCollection })));
      const additionalRequestContentConfigs = [requestContentConfig];
      const expectedCollection: IRequestContentConfig[] = [...additionalRequestContentConfigs, ...requestContentConfigCollection];
      jest.spyOn(requestContentConfigService, 'addRequestContentConfigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ requestConfig });
      comp.ngOnInit();

      expect(requestContentConfigService.query).toHaveBeenCalled();
      expect(requestContentConfigService.addRequestContentConfigToCollectionIfMissing).toHaveBeenCalledWith(
        requestContentConfigCollection,
        ...additionalRequestContentConfigs.map(expect.objectContaining),
      );
      expect(comp.requestContentConfigsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const requestConfig: IRequestConfig = { id: 456 };
      const requestContentConfig: IRequestContentConfig = { id: 721 };
      requestConfig.requestContentConfig = requestContentConfig;

      activatedRoute.data = of({ requestConfig });
      comp.ngOnInit();

      expect(comp.requestContentConfigsSharedCollection).toContain(requestContentConfig);
      expect(comp.requestConfig).toEqual(requestConfig);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequestConfig>>();
      const requestConfig = { id: 123 };
      jest.spyOn(requestConfigFormService, 'getRequestConfig').mockReturnValue(requestConfig);
      jest.spyOn(requestConfigService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestConfig });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requestConfig }));
      saveSubject.complete();

      // THEN
      expect(requestConfigFormService.getRequestConfig).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(requestConfigService.update).toHaveBeenCalledWith(expect.objectContaining(requestConfig));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequestConfig>>();
      const requestConfig = { id: 123 };
      jest.spyOn(requestConfigFormService, 'getRequestConfig').mockReturnValue({ id: null });
      jest.spyOn(requestConfigService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestConfig: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requestConfig }));
      saveSubject.complete();

      // THEN
      expect(requestConfigFormService.getRequestConfig).toHaveBeenCalled();
      expect(requestConfigService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequestConfig>>();
      const requestConfig = { id: 123 };
      jest.spyOn(requestConfigService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestConfig });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(requestConfigService.update).toHaveBeenCalled();
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
