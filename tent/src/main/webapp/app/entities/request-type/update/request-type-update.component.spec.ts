import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IRequestInfo } from 'app/entities/request-info/request-info.model';
import { RequestInfoService } from 'app/entities/request-info/service/request-info.service';
import { IRequestConfig } from 'app/entities/request-config/request-config.model';
import { RequestConfigService } from 'app/entities/request-config/service/request-config.service';
import { IRequestType } from '../request-type.model';
import { RequestTypeService } from '../service/request-type.service';
import { RequestTypeFormService } from './request-type-form.service';

import { RequestTypeUpdateComponent } from './request-type-update.component';

describe('RequestType Management Update Component', () => {
  let comp: RequestTypeUpdateComponent;
  let fixture: ComponentFixture<RequestTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let requestTypeFormService: RequestTypeFormService;
  let requestTypeService: RequestTypeService;
  let requestInfoService: RequestInfoService;
  let requestConfigService: RequestConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RequestTypeUpdateComponent],
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
      .overrideTemplate(RequestTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RequestTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    requestTypeFormService = TestBed.inject(RequestTypeFormService);
    requestTypeService = TestBed.inject(RequestTypeService);
    requestInfoService = TestBed.inject(RequestInfoService);
    requestConfigService = TestBed.inject(RequestConfigService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RequestInfo query and add missing value', () => {
      const requestType: IRequestType = { id: 456 };
      const requestInfo: IRequestInfo = { id: 9002 };
      requestType.requestInfo = requestInfo;

      const requestInfoCollection: IRequestInfo[] = [{ id: 8573 }];
      jest.spyOn(requestInfoService, 'query').mockReturnValue(of(new HttpResponse({ body: requestInfoCollection })));
      const additionalRequestInfos = [requestInfo];
      const expectedCollection: IRequestInfo[] = [...additionalRequestInfos, ...requestInfoCollection];
      jest.spyOn(requestInfoService, 'addRequestInfoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ requestType });
      comp.ngOnInit();

      expect(requestInfoService.query).toHaveBeenCalled();
      expect(requestInfoService.addRequestInfoToCollectionIfMissing).toHaveBeenCalledWith(
        requestInfoCollection,
        ...additionalRequestInfos.map(expect.objectContaining),
      );
      expect(comp.requestInfosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call RequestConfig query and add missing value', () => {
      const requestType: IRequestType = { id: 456 };
      const requestConfig: IRequestConfig = { id: 2456 };
      requestType.requestConfig = requestConfig;

      const requestConfigCollection: IRequestConfig[] = [{ id: 25704 }];
      jest.spyOn(requestConfigService, 'query').mockReturnValue(of(new HttpResponse({ body: requestConfigCollection })));
      const additionalRequestConfigs = [requestConfig];
      const expectedCollection: IRequestConfig[] = [...additionalRequestConfigs, ...requestConfigCollection];
      jest.spyOn(requestConfigService, 'addRequestConfigToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ requestType });
      comp.ngOnInit();

      expect(requestConfigService.query).toHaveBeenCalled();
      expect(requestConfigService.addRequestConfigToCollectionIfMissing).toHaveBeenCalledWith(
        requestConfigCollection,
        ...additionalRequestConfigs.map(expect.objectContaining),
      );
      expect(comp.requestConfigsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const requestType: IRequestType = { id: 456 };
      const requestInfo: IRequestInfo = { id: 14985 };
      requestType.requestInfo = requestInfo;
      const requestConfig: IRequestConfig = { id: 17454 };
      requestType.requestConfig = requestConfig;

      activatedRoute.data = of({ requestType });
      comp.ngOnInit();

      expect(comp.requestInfosSharedCollection).toContain(requestInfo);
      expect(comp.requestConfigsSharedCollection).toContain(requestConfig);
      expect(comp.requestType).toEqual(requestType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequestType>>();
      const requestType = { id: 123 };
      jest.spyOn(requestTypeFormService, 'getRequestType').mockReturnValue(requestType);
      jest.spyOn(requestTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requestType }));
      saveSubject.complete();

      // THEN
      expect(requestTypeFormService.getRequestType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(requestTypeService.update).toHaveBeenCalledWith(expect.objectContaining(requestType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequestType>>();
      const requestType = { id: 123 };
      jest.spyOn(requestTypeFormService, 'getRequestType').mockReturnValue({ id: null });
      jest.spyOn(requestTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requestType }));
      saveSubject.complete();

      // THEN
      expect(requestTypeFormService.getRequestType).toHaveBeenCalled();
      expect(requestTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequestType>>();
      const requestType = { id: 123 };
      jest.spyOn(requestTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(requestTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareRequestInfo', () => {
      it('Should forward to requestInfoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(requestInfoService, 'compareRequestInfo');
        comp.compareRequestInfo(entity, entity2);
        expect(requestInfoService.compareRequestInfo).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareRequestConfig', () => {
      it('Should forward to requestConfigService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(requestConfigService, 'compareRequestConfig');
        comp.compareRequestConfig(entity, entity2);
        expect(requestConfigService.compareRequestConfig).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
