import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IRequestContent } from 'app/entities/request-content/request-content.model';
import { RequestContentService } from 'app/entities/request-content/service/request-content.service';
import { RequestInfoService } from '../service/request-info.service';
import { IRequestInfo } from '../request-info.model';
import { RequestInfoFormService } from './request-info-form.service';

import { RequestInfoUpdateComponent } from './request-info-update.component';

describe('RequestInfo Management Update Component', () => {
  let comp: RequestInfoUpdateComponent;
  let fixture: ComponentFixture<RequestInfoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let requestInfoFormService: RequestInfoFormService;
  let requestInfoService: RequestInfoService;
  let requestContentService: RequestContentService;

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
    requestContentService = TestBed.inject(RequestContentService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RequestContent query and add missing value', () => {
      const requestInfo: IRequestInfo = { id: 456 };
      const requestContent: IRequestContent = { id: 7604 };
      requestInfo.requestContent = requestContent;

      const requestContentCollection: IRequestContent[] = [{ id: 26397 }];
      jest.spyOn(requestContentService, 'query').mockReturnValue(of(new HttpResponse({ body: requestContentCollection })));
      const additionalRequestContents = [requestContent];
      const expectedCollection: IRequestContent[] = [...additionalRequestContents, ...requestContentCollection];
      jest.spyOn(requestContentService, 'addRequestContentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ requestInfo });
      comp.ngOnInit();

      expect(requestContentService.query).toHaveBeenCalled();
      expect(requestContentService.addRequestContentToCollectionIfMissing).toHaveBeenCalledWith(
        requestContentCollection,
        ...additionalRequestContents.map(expect.objectContaining),
      );
      expect(comp.requestContentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const requestInfo: IRequestInfo = { id: 456 };
      const requestContent: IRequestContent = { id: 23726 };
      requestInfo.requestContent = requestContent;

      activatedRoute.data = of({ requestInfo });
      comp.ngOnInit();

      expect(comp.requestContentsSharedCollection).toContain(requestContent);
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
    describe('compareRequestContent', () => {
      it('Should forward to requestContentService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(requestContentService, 'compareRequestContent');
        comp.compareRequestContent(entity, entity2);
        expect(requestContentService.compareRequestContent).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
