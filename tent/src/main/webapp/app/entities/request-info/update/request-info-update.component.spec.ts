import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IRequestType } from 'app/entities/request-type/request-type.model';
import { RequestTypeService } from 'app/entities/request-type/service/request-type.service';
import { ISourceSystem } from 'app/entities/source-system/source-system.model';
import { SourceSystemService } from 'app/entities/source-system/service/source-system.service';
import { IRequestInfo } from '../request-info.model';
import { RequestInfoService } from '../service/request-info.service';
import { RequestInfoFormService } from './request-info-form.service';

import { RequestInfoUpdateComponent } from './request-info-update.component';

describe('RequestInfo Management Update Component', () => {
  let comp: RequestInfoUpdateComponent;
  let fixture: ComponentFixture<RequestInfoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let requestInfoFormService: RequestInfoFormService;
  let requestInfoService: RequestInfoService;
  let requestTypeService: RequestTypeService;
  let sourceSystemService: SourceSystemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RequestInfoUpdateComponent],
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
      .overrideTemplate(RequestInfoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RequestInfoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    requestInfoFormService = TestBed.inject(RequestInfoFormService);
    requestInfoService = TestBed.inject(RequestInfoService);
    requestTypeService = TestBed.inject(RequestTypeService);
    sourceSystemService = TestBed.inject(SourceSystemService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RequestType query and add missing value', () => {
      const requestInfo: IRequestInfo = { id: 456 };
      const requestType: IRequestType = { id: 30403 };
      requestInfo.requestType = requestType;

      const requestTypeCollection: IRequestType[] = [{ id: 9350 }];
      jest.spyOn(requestTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: requestTypeCollection })));
      const additionalRequestTypes = [requestType];
      const expectedCollection: IRequestType[] = [...additionalRequestTypes, ...requestTypeCollection];
      jest.spyOn(requestTypeService, 'addRequestTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ requestInfo });
      comp.ngOnInit();

      expect(requestTypeService.query).toHaveBeenCalled();
      expect(requestTypeService.addRequestTypeToCollectionIfMissing).toHaveBeenCalledWith(
        requestTypeCollection,
        ...additionalRequestTypes.map(expect.objectContaining),
      );
      expect(comp.requestTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SourceSystem query and add missing value', () => {
      const requestInfo: IRequestInfo = { id: 456 };
      const requestSource: ISourceSystem = { id: 3562 };
      requestInfo.requestSource = requestSource;

      const sourceSystemCollection: ISourceSystem[] = [{ id: 3249 }];
      jest.spyOn(sourceSystemService, 'query').mockReturnValue(of(new HttpResponse({ body: sourceSystemCollection })));
      const additionalSourceSystems = [requestSource];
      const expectedCollection: ISourceSystem[] = [...additionalSourceSystems, ...sourceSystemCollection];
      jest.spyOn(sourceSystemService, 'addSourceSystemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ requestInfo });
      comp.ngOnInit();

      expect(sourceSystemService.query).toHaveBeenCalled();
      expect(sourceSystemService.addSourceSystemToCollectionIfMissing).toHaveBeenCalledWith(
        sourceSystemCollection,
        ...additionalSourceSystems.map(expect.objectContaining),
      );
      expect(comp.sourceSystemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const requestInfo: IRequestInfo = { id: 456 };
      const requestType: IRequestType = { id: 29672 };
      requestInfo.requestType = requestType;
      const requestSource: ISourceSystem = { id: 8070 };
      requestInfo.requestSource = requestSource;

      activatedRoute.data = of({ requestInfo });
      comp.ngOnInit();

      expect(comp.requestTypesSharedCollection).toContain(requestType);
      expect(comp.sourceSystemsSharedCollection).toContain(requestSource);
      expect(comp.requestInfo).toEqual(requestInfo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequestInfo>>();
      const requestInfo = { id: 123 };
      jest.spyOn(requestInfoFormService, 'getRequestInfo').mockReturnValue(requestInfo);
      jest.spyOn(requestInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requestInfo }));
      saveSubject.complete();

      // THEN
      expect(requestInfoFormService.getRequestInfo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(requestInfoService.update).toHaveBeenCalledWith(expect.objectContaining(requestInfo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequestInfo>>();
      const requestInfo = { id: 123 };
      jest.spyOn(requestInfoFormService, 'getRequestInfo').mockReturnValue({ id: null });
      jest.spyOn(requestInfoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestInfo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requestInfo }));
      saveSubject.complete();

      // THEN
      expect(requestInfoFormService.getRequestInfo).toHaveBeenCalled();
      expect(requestInfoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequestInfo>>();
      const requestInfo = { id: 123 };
      jest.spyOn(requestInfoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestInfo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(requestInfoService.update).toHaveBeenCalled();
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

    describe('compareSourceSystem', () => {
      it('Should forward to sourceSystemService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(sourceSystemService, 'compareSourceSystem');
        comp.compareSourceSystem(entity, entity2);
        expect(sourceSystemService.compareSourceSystem).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
