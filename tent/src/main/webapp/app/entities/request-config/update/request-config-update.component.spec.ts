import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IRequestType } from 'app/entities/request-type/request-type.model';
import { RequestTypeService } from 'app/entities/request-type/service/request-type.service';
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
  let requestTypeService: RequestTypeService;

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
    requestTypeService = TestBed.inject(RequestTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RequestType query and add missing value', () => {
      const requestConfig: IRequestConfig = { id: 456 };
      const requestType: IRequestType = { id: 11602 };
      requestConfig.requestType = requestType;

      const requestTypeCollection: IRequestType[] = [{ id: 17123 }];
      jest.spyOn(requestTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: requestTypeCollection })));
      const additionalRequestTypes = [requestType];
      const expectedCollection: IRequestType[] = [...additionalRequestTypes, ...requestTypeCollection];
      jest.spyOn(requestTypeService, 'addRequestTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ requestConfig });
      comp.ngOnInit();

      expect(requestTypeService.query).toHaveBeenCalled();
      expect(requestTypeService.addRequestTypeToCollectionIfMissing).toHaveBeenCalledWith(
        requestTypeCollection,
        ...additionalRequestTypes.map(expect.objectContaining),
      );
      expect(comp.requestTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const requestConfig: IRequestConfig = { id: 456 };
      const requestType: IRequestType = { id: 1219 };
      requestConfig.requestType = requestType;

      activatedRoute.data = of({ requestConfig });
      comp.ngOnInit();

      expect(comp.requestTypesSharedCollection).toContain(requestType);
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
    describe('compareRequestType', () => {
      it('Should forward to requestTypeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(requestTypeService, 'compareRequestType');
        comp.compareRequestType(entity, entity2);
        expect(requestTypeService.compareRequestType).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
