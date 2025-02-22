import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { RequestTypeService } from '../service/request-type.service';
import { IRequestType } from '../request-type.model';
import { RequestTypeFormService } from './request-type-form.service';

import { RequestTypeUpdateComponent } from './request-type-update.component';

describe('RequestType Management Update Component', () => {
  let comp: RequestTypeUpdateComponent;
  let fixture: ComponentFixture<RequestTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let requestTypeFormService: RequestTypeFormService;
  let requestTypeService: RequestTypeService;

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const requestType: IRequestType = { id: 456 };

      activatedRoute.data = of({ requestType });
      comp.ngOnInit();

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
});
