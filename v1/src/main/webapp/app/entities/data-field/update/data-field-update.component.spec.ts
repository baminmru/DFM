import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DataFieldFormService } from './data-field-form.service';
import { DataFieldService } from '../service/data-field.service';
import { IDataField } from '../data-field.model';

import { DataFieldUpdateComponent } from './data-field-update.component';

describe('DataField Management Update Component', () => {
  let comp: DataFieldUpdateComponent;
  let fixture: ComponentFixture<DataFieldUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dataFieldFormService: DataFieldFormService;
  let dataFieldService: DataFieldService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), DataFieldUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(DataFieldUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DataFieldUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dataFieldFormService = TestBed.inject(DataFieldFormService);
    dataFieldService = TestBed.inject(DataFieldService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const dataField: IDataField = { id: 456 };

      activatedRoute.data = of({ dataField });
      comp.ngOnInit();

      expect(comp.dataField).toEqual(dataField);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataField>>();
      const dataField = { id: 123 };
      jest.spyOn(dataFieldFormService, 'getDataField').mockReturnValue(dataField);
      jest.spyOn(dataFieldService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataField });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dataField }));
      saveSubject.complete();

      // THEN
      expect(dataFieldFormService.getDataField).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dataFieldService.update).toHaveBeenCalledWith(expect.objectContaining(dataField));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataField>>();
      const dataField = { id: 123 };
      jest.spyOn(dataFieldFormService, 'getDataField').mockReturnValue({ id: null });
      jest.spyOn(dataFieldService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataField: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dataField }));
      saveSubject.complete();

      // THEN
      expect(dataFieldFormService.getDataField).toHaveBeenCalled();
      expect(dataFieldService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDataField>>();
      const dataField = { id: 123 };
      jest.spyOn(dataFieldService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dataField });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dataFieldService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
