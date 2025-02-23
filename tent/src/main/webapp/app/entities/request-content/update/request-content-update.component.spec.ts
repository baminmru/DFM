import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IRequestInfo } from 'app/entities/request-info/request-info.model';
import { RequestInfoService } from 'app/entities/request-info/service/request-info.service';
import { RequestContentService } from '../service/request-content.service';
import { IRequestContent } from '../request-content.model';
import { RequestContentFormService } from './request-content-form.service';

import { RequestContentUpdateComponent } from './request-content-update.component';

describe('RequestContent Management Update Component', () => {
  let comp: RequestContentUpdateComponent;
  let fixture: ComponentFixture<RequestContentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let requestContentFormService: RequestContentFormService;
  let requestContentService: RequestContentService;
  let requestInfoService: RequestInfoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RequestContentUpdateComponent],
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
      .overrideTemplate(RequestContentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RequestContentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    requestContentFormService = TestBed.inject(RequestContentFormService);
    requestContentService = TestBed.inject(RequestContentService);
    requestInfoService = TestBed.inject(RequestInfoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RequestInfo query and add missing value', () => {
      const requestContent: IRequestContent = { id: 456 };
      const requestInfoId: IRequestInfo = { id: 27614 };
      requestContent.requestInfoId = requestInfoId;

      const requestInfoCollection: IRequestInfo[] = [{ id: 12179 }];
      jest.spyOn(requestInfoService, 'query').mockReturnValue(of(new HttpResponse({ body: requestInfoCollection })));
      const additionalRequestInfos = [requestInfoId];
      const expectedCollection: IRequestInfo[] = [...additionalRequestInfos, ...requestInfoCollection];
      jest.spyOn(requestInfoService, 'addRequestInfoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ requestContent });
      comp.ngOnInit();

      expect(requestInfoService.query).toHaveBeenCalled();
      expect(requestInfoService.addRequestInfoToCollectionIfMissing).toHaveBeenCalledWith(
        requestInfoCollection,
        ...additionalRequestInfos.map(expect.objectContaining),
      );
      expect(comp.requestInfosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const requestContent: IRequestContent = { id: 456 };
      const requestInfoId: IRequestInfo = { id: 27567 };
      requestContent.requestInfoId = requestInfoId;

      activatedRoute.data = of({ requestContent });
      comp.ngOnInit();

      expect(comp.requestInfosSharedCollection).toContain(requestInfoId);
      expect(comp.requestContent).toEqual(requestContent);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequestContent>>();
      const requestContent = { id: 123 };
      jest.spyOn(requestContentFormService, 'getRequestContent').mockReturnValue(requestContent);
      jest.spyOn(requestContentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestContent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requestContent }));
      saveSubject.complete();

      // THEN
      expect(requestContentFormService.getRequestContent).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(requestContentService.update).toHaveBeenCalledWith(expect.objectContaining(requestContent));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequestContent>>();
      const requestContent = { id: 123 };
      jest.spyOn(requestContentFormService, 'getRequestContent').mockReturnValue({ id: null });
      jest.spyOn(requestContentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestContent: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: requestContent }));
      saveSubject.complete();

      // THEN
      expect(requestContentFormService.getRequestContent).toHaveBeenCalled();
      expect(requestContentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRequestContent>>();
      const requestContent = { id: 123 };
      jest.spyOn(requestContentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ requestContent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(requestContentService.update).toHaveBeenCalled();
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
  });
});
